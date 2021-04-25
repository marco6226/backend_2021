/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.com;

import co.sigess.entities.ado.Modulo;
import co.sigess.entities.emp.Empresa;
import co.sigess.restful.CriteriaFilter;
import co.sigess.restful.Filter;
import co.sigess.restful.FilterQuery;
import co.sigess.util.Util;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.security.acl.Group;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 *
 * @author fmoreno
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T create(T entity) throws Exception {
         try {
        getEntityManager().persist(entity);
    } catch (ConstraintViolationException e) {
        // Aqui tira los errores de constraint
        for (ConstraintViolation actual : e.getConstraintViolations()) {
            System.out.println(actual.toString());
        }
    }
        return entity;
    }

    public T edit(T entity) throws Exception {
        getEntityManager().merge(entity);
        return entity;
    }

    public void remove(T entity) throws Exception {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public Long countWithFilter(FilterQuery filterQuery) throws IOException, ParseException, NoSuchFieldException {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
       
        cq.select(cb.count(root));
        cq.where(this.generarPredicados(filterQuery, cb, root));
        Query query = getEntityManager().createQuery(cq);
        
        return (Long) query.getSingleResult();
    }

    public List<T> findWithFilter(FilterQuery filterQuery) throws IOException, ParseException, NoSuchFieldException {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);

        boolean queryByField = filterQuery.getFieldList() != null;
        if (queryByField) {
            Selection[] selections = this.generarSelecciones(filterQuery, root);
            cq.multiselect(selections).distinct(true);
        } else {
            cq.select(root);
        }
        
       
        cq.where(this.generarPredicados(filterQuery, cb, root));
        if (filterQuery.getSortField() != null && !filterQuery.getSortField().isEmpty()) {
            Order order = (filterQuery.getSortOrder() != null && filterQuery.getSortOrder() < 0) ? cb.asc(this.recursiveSearchField(filterQuery.getSortField(), root)) : cb.desc(this.recursiveSearchField(filterQuery.getSortField(), root));
            cq.orderBy(order);
        }
        Query query = getEntityManager().createQuery(cq);
        if (filterQuery.getOffset() != null && filterQuery.getOffset() >= 0) {
            query.setFirstResult(filterQuery.getOffset());
        }
        if (filterQuery.getRows() != null && filterQuery.getRows() >= 0) {
            query.setMaxResults(filterQuery.getRows());
        }

        if (queryByField) {
            List mapList = new ArrayList<>();           
            query.getResultList().forEach(
                    resultado -> {
                        try {
                            Map<String, Object> rowMap = new HashMap(filterQuery.getFieldList().length);
                            int i = 0;
                            for (String field : filterQuery.getFieldList()) {
                                rowMap.put(field, resultado instanceof Object[] ? ((Object[]) resultado)[i++] : resultado);
                            }
                            mapList.add(rowMap);
                        } catch (IOException ex) {
                            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
            return mapList;
        } else {
            return query.getResultList();
        }
    }

    private Selection[] generarSelecciones(FilterQuery filterQuery, Root<T> root) throws IOException {

        List<Selection> selectList = new ArrayList<>();
        if (filterQuery.getFieldList() == null) {
            selectList.add(root);
        } else {
            for (String field : filterQuery.getFieldList()) {
                Path pathField = root;

                if (field.contains("_")) {
                    String[] subFieldArray = field.split("_");
                    for (String subField : subFieldArray) {
                        pathField = pathField instanceof Root ? ((Root<T>) pathField).join(subField, subField.endsWith("PK") ? JoinType.INNER : JoinType.LEFT) : pathField.get(subField);
                    }
                } else {
                    pathField = pathField.get(field);
                }
                ((Selection) pathField).alias(field);
                selectList.add(pathField);
            }
        }

        return selectList.toArray(new Selection[0]);
    }

    private Predicate[] generarPredicados(FilterQuery filterQuery, CriteriaBuilder cb, Root<T> root) throws ParseException, NoSuchFieldException, IOException {

        List<Filter> list = filterQuery.getFilterList();
        List<Predicate> predicateList = new ArrayList<>();
        List<Predicate> betweenPredicateList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Filter filter = list.get(i);
            if (filter.getValue1() == null && !(filter.getCriteriaEnum().equals(CriteriaFilter.IS_NOT_NULL) || filter.getCriteriaEnum().equals(CriteriaFilter.IS_NULL))) {
                continue;
            }

            Class type = recursiveSearchType(filter.getField(), entityClass);
            Predicate predicate;
            Path expression = recursiveSearchField(filter.getField(), root);
            Object value = filter.isExpression() ? recursiveSearchField(filter.getValue1(), root) : filter.getValue1();
            switch (filter.getCriteriaEnum()) {
                case EQUALS:
                    if (filter.isExpression()) {
                        predicate = cb.equal(expression, (Path) value);
                    } else {
                        predicate = cb.equal(expression, convertType(expression.getJavaType(), (String) value));
                    }
                    break;
                case IS_NULL:
                    predicate = cb.isNull(expression);
                    break;
                case IS_NOT_NULL:
                    predicate = cb.isNotNull(expression);
                    break;
                case LIKE:
                    predicate = cb.like(expression, filter.getValue1());
                    break;
                case NOT_EQUALS:
                    if (filter.isExpression()) {
                        predicate = cb.notEqual(expression, (Path) value);
                    } else if (expression.getJavaType().isEnum()) {
                        predicate = cb.notEqual(expression, Enum.valueOf(expression.getJavaType(), (String) value));
                    } else {
                        predicate = cb.notEqual(expression, (String) value);
                    }
                    break;
                case CONTAINS:
                    predicate = expression.in((Object[]) filter.getValueArray());
                    break;
                case NOT_CONTAINS:
                    predicate = cb.not(expression.in((Object[]) filter.getValueArray()));
                    break;
                case GREATER_THAN:
                    predicate = cb.gt(expression, NumberFormat.getInstance().parse(filter.getValue1()));
                    break;
                case LOWER_THAN:
                    predicate = cb.lt(expression, NumberFormat.getInstance().parse(filter.getValue1()));
                    break;
                case LESS_THAN_OR_EQUAL:
                    if (type == Date.class) {
                        Date value1 = Util.SIMPLE_DATE_FORMAT.parse(filter.getValue1());
                        predicate = cb.le(expression, value1.getTime() + (1000 * 60 * 60 * 24));
                    } else {
                        predicate = cb.le(expression, NumberFormat.getInstance().parse(filter.getValue1()));
                    }
                    break;
                case GREATER_THAN_OR_EQUAL:
                    if (type == Date.class) {
                        Date value1 = Util.SIMPLE_DATE_FORMAT.parse(filter.getValue1());
                        predicate = cb.ge(expression, value1.getTime());
                    } else {
                        predicate = cb.ge(expression, NumberFormat.getInstance().parse(filter.getValue1()));
                    }
                    break;
                case BETWEEN:
                    if (type == Date.class) {
                        Date value1 = Util.SIMPLE_DATE_FORMAT.parse(filter.getValue1());
                        Date value2 = filter.getValue2() == null ? null : new Date(Util.SIMPLE_DATE_FORMAT.parse(filter.getValue2()).getTime() + (1000 * 60 * 60 * 24));
                        predicate = cb.between(expression, value1, value2);
                    } else if (type == Integer.class) {
                        predicate = cb.between(expression, Integer.parseInt(filter.getValue1()), Integer.parseInt(filter.getValue2()));
                    } else {
                        predicate = cb.between(expression, filter.getValue1(), filter.getValue2());
                    }
                    betweenPredicateList.add(predicate);
                    continue;
                default:
                    throw new IllegalArgumentException("Criterio de filtrado no válido: " + filter.getCriteriaEnum());
            }
            predicateList.add(predicate);
        }

        if (!betweenPredicateList.isEmpty()) {
            Predicate[] arrayBetweenPred = betweenPredicateList.toArray(new Predicate[0]);
            Predicate orPredicate = cb.or(arrayBetweenPred);
            predicateList.add(orPredicate);
        }

        /*
        Predicate predicate;
        Predicate[] arrayPred = predicateList.toArray(new Predicate[0]);
        switch (filterQuery.getLogicOperation()) {
            case OR:
                predicate = cb.or(arrayPred);
                break;
            case AND:
            default:
                predicate = cb.and(arrayPred);
                break;
        }
        Predicate[] arrayPredicates = {predicate};
         */
        Predicate[] arrayPredicates = predicateList.toArray(new Predicate[0]);
        return arrayPredicates;
    }

    public List<T> findAllByEmpresa(Integer empresaId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(root).where(cb.equal(root.get("empresa"), new Empresa(empresaId))).orderBy(cb.asc(root.get("id")));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count(Integer empresaId) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        Root<T> root = cq.from(entityClass);
        cq.select(cb.count(root)).where(cb.equal(root.get("empresa"), new Empresa(empresaId)));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    private Class recursiveSearchType(String field, Class<T> entityClass) throws NoSuchFieldException {
        if (field.contains(".")) {
            int dotIndex = field.indexOf(".");
            String fieldPath = field.substring(dotIndex + 1, field.length());
            String fieldParent = field.substring(0, dotIndex);
            Class clazzParent = entityClass.getDeclaredField(fieldParent).getType();

            if (clazzParent == List.class) {
                Field stringListField = entityClass.getDeclaredField(fieldParent);
                ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
                clazzParent = (Class<?>) stringListType.getActualTypeArguments()[0];
            }

            return recursiveSearchType(fieldPath, clazzParent);
        } else {
            Class type = entityClass.getDeclaredField(field).getType();
            return type;
        }
    }

    private Path recursiveSearchField(String field, Path<T> path) {
        if (field.contains(".")) {
            int dotIndex = field.indexOf(".");
            String fieldPath = field.substring(dotIndex + 1, field.length());
            String fieldParent = field.substring(0, dotIndex);
            Path pathChild;
            if (path instanceof Root) {
                Root<T> root = (Root<T>) path;
                pathChild = root.join(fieldParent, fieldParent.endsWith("PK") ? JoinType.INNER : JoinType.LEFT);
            } else {
                pathChild = path.get(fieldParent);
            }
            return recursiveSearchField(fieldPath, pathChild);
        } else {
            Path expression = path.get(field);
            return expression;

        }
    }

    private Object convertType(Class clazz, String value) {
        if (clazz == Modulo.class) {
            return Modulo.valueOf(value);
        } else {
            return value;
        }
    }

    
    
    public Class<T> getEntityClass() {
        return entityClass;
    }
}
