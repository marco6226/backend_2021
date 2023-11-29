/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful.ipr;

import co.sigess.entities.ado.Directorio;
import co.sigess.entities.ado.Documento;
import co.sigess.entities.ado.Modulo;
import co.sigess.entities.emp.Area;
import co.sigess.entities.emp.Empresa;
import co.sigess.entities.emp.Plantas;
import co.sigess.entities.ipr.MatrizPeligros;
import co.sigess.entities.ipr.ViewMatrizPeligros;
import co.sigess.facade.ado.DirectorioFacade;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.facade.emp.PlantasFacade;
import co.sigess.facade.ipr.ViewMatrizPeligrosFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.FileUtil;
import static co.sigess.util.FileUtil.ROOT_DIR;
import co.sigess.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Usuario
 */
@Secured
@Path("vmatrizP")
public class ViewMatrizPeligrosREST  extends ServiceREST{
    @EJB
    private  ViewMatrizPeligrosFacade viewMatrizPeligrosFacade;
    
    @EJB
    private  DirectorioFacade directorioFacade;
    
    @EJB
    private  PlantasFacade plantasFacade;
    
    @EJB
    private  AreaFacade areaFacade;
    
    public ViewMatrizPeligrosREST() {
        super(ViewMatrizPeligrosFacade.class);
    }
    
    @GET
    @Path("vmpRegistroFilter")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? viewMatrizPeligrosFacade.countWithFilter(filterQuery) : -1;
            List list = viewMatrizPeligrosFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, ViewMatrizPeligrosREST.class);
        }
    }
    
    @GET
    @Path("vmpExcelConsolidado")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response vmpExcelConsolidado(@BeanParam FilterQuery filterQuery){
        try{
            Class<?> clase = ViewMatrizPeligros.class;
//            Method[] metodos = clase.getDeclaredMethods();
            List<Method> metodosEnOrden = new ArrayList<>();
            metodosEnOrden.add(clase.getMethod("getId"));
            metodosEnOrden.add(clase.getMethod("getArea"));
            metodosEnOrden.add(clase.getMethod("getProceso"));
            metodosEnOrden.add(clase.getMethod("getSubproceso"));
            metodosEnOrden.add(clase.getMethod("getActividades"));
            metodosEnOrden.add(clase.getMethod("getRutinaria"));
            metodosEnOrden.add(clase.getMethod("getPropios"));
            metodosEnOrden.add(clase.getMethod("getTemporales"));
            metodosEnOrden.add(clase.getMethod("getContratistas"));
            metodosEnOrden.add(clase.getMethod("getTotal"));
            metodosEnOrden.add(clase.getMethod("getPeligro"));
            metodosEnOrden.add(clase.getMethod("getDescripcionPeligro"));
            metodosEnOrden.add(clase.getMethod("getFuenteGeneradora"));
            metodosEnOrden.add(clase.getMethod("getEfectos"));
            metodosEnOrden.add(clase.getMethod("getEfectos"));
            metodosEnOrden.add(clase.getMethod("getIngenieria"));
            metodosEnOrden.add(clase.getMethod("getAdministrativos"));
            metodosEnOrden.add(clase.getMethod("getElementospro"));
            metodosEnOrden.add(clase.getMethod("getNdInicial"));
            metodosEnOrden.add(clase.getMethod("getNeInicial"));
            metodosEnOrden.add(clase.getMethod("getNpInicial"));
            metodosEnOrden.add(clase.getMethod("getNpInterpretacionInicial"));
            metodosEnOrden.add(clase.getMethod("getNcInicial"));
            metodosEnOrden.add(clase.getMethod("getNrInicial"));
            metodosEnOrden.add(clase.getMethod("getCuantitativoInicial"));
            metodosEnOrden.add(clase.getMethod("getCualitativoInicial"));
            metodosEnOrden.add(clase.getMethod("getDescripcionInicial"));
            metodosEnOrden.add(clase.getMethod("getPlanAccion"));
            metodosEnOrden.add(clase.getMethod("getPlanAccion"));
            metodosEnOrden.add(clase.getMethod("getPlanAccion"));
            metodosEnOrden.add(clase.getMethod("getPlanAccion"));
            metodosEnOrden.add(clase.getMethod("getPlanAccion"));
            metodosEnOrden.add(clase.getMethod("getNdResidual"));
            metodosEnOrden.add(clase.getMethod("getNeResidual"));
            metodosEnOrden.add(clase.getMethod("getNpResidual"));
            metodosEnOrden.add(clase.getMethod("getNpInterpretacionResidual"));
            metodosEnOrden.add(clase.getMethod("getNcResidual"));
            metodosEnOrden.add(clase.getMethod("getNrResidual"));
            metodosEnOrden.add(clase.getMethod("getCuantitativoResidual"));
            metodosEnOrden.add(clase.getMethod("getCualitativoResidual"));
            metodosEnOrden.add(clase.getMethod("getDescripcionResidual"));
            metodosEnOrden.add(clase.getMethod("getControlesEjecutados"));
            metodosEnOrden.add(clase.getMethod("getControlesPropuestos"));
            metodosEnOrden.add(clase.getMethod("getCumplimiento"));
            metodosEnOrden.add(clase.getMethod("getAtAsociados"));
            metodosEnOrden.add(clase.getMethod("getElAsociados"));
            metodosEnOrden.add(clase.getMethod("getEstado"));
        
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? viewMatrizPeligrosFacade.countWithFilter(filterQuery) : -1;
            List<ViewMatrizPeligros> list = viewMatrizPeligrosFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            
            InputStream fis = new FileInputStream(ROOT_DIR + "excel-plantilla" + File.separator + "consolidadoexcelcorona.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rowNum=5;
            
            int startRow = 4; // Fila 4 (índice 3)
            int startColumn = 0; // Columna A (índice 0)
            int endColumn = 46; // Columna AT (índice 45)
            
            Row row1 = sheet.createRow(1);
            Cell cell1 = row1.createCell(2);
            cell1.setCellValue("Ubicación:");
            cell1 = row1.createCell(3);
            cell1.setCellValue(list.get(0).getDivision());
            cell1 = row1.createCell(5);
            cell1.setCellValue("Planta:");
            cell1 = row1.createCell(6);
            cell1.setCellValue(list.get(0).getPlanta());
           
            Integer cont=0;
            String descripcion="";
            String jerarquia="";
            for (ViewMatrizPeligros element : list) {
                Row row = sheet.createRow(rowNum);
                //System.out.println(rowNum-5);
                int i=0;
                for(Method metodo : metodosEnOrden){
                    

                    try {
                        Object resultado = metodo.invoke(element);
                        if(!resultado.toString().isEmpty()){
                            Cell cell = row.createCell(i);
                            String texto = resultado.toString();
                           
                            if((i>=15 && i<=17) || (i>=27 && i<=31)){
                                if(i==27)jerarquia="Eliminación";
                                if(i==28)jerarquia="Sustitución";
                                if(i==29)jerarquia="Control de ingeniería";
                                if(i==30)jerarquia="Controles administrativos";
                                if(i==31)jerarquia="Elementos de protección personal";
                                JsonArray jsonResultado = JsonParser.parseString(resultado.toString()).getAsJsonArray();

                                if (jsonResultado instanceof JsonArray) {
                                    cont=0;
                                    descripcion="";
                                    for(JsonElement planAccion : jsonResultado){
                                        JsonObject pAccion = planAccion.getAsJsonObject();
                                        if(i>=27 && i<=31){
                                            if(pAccion.get("jerarquia").getAsString().equals(jerarquia)){
                                                cont++;
                                                descripcion+=cont+". "+pAccion.get("descripcion").getAsString()+"\n";
                                            }
                                        }else{
                                            cont++;
                                            descripcion+=cont+". "+pAccion.get("descripcion").getAsString()+"\n";
                                        }
                                    }
                                    cell.setCellValue(descripcion);
                                }                     
                            }else if(i==26 || i==37){
                                                        
                                Object resultado2 = list.get(rowNum-5).getNrInicial();
                                long NR = Long.parseLong(resultado2.toString());
                                if(20>=NR && NR>=0){
                                    cell.setCellValue("Mantenga los controles existentes");
                                }else if(120>=NR && NR>=40){
                                    cell.setCellValue("1. Mantenga los controles existentes \n 2. Identifique mejoras");
                                }else if(500>=NR && NR>=150){
                                    cell.setCellValue("1. Intervenga de inmediato \n 2. Implemente controles (existentes o adicionales) \n 3. Identifique desviaciones si existe");
                                }else if(NR>=600){
                                    cell.setCellValue("1. Suspenda la actividad. \n 2. intervenga de inmediato \n 3.Implemente controles. Intervenir es comunicar la situación a los diferentes responsables y ejecutores de la tarea (requiere reporte y gestión de acto y condiciones) Alto");
                                }
                            }
                            else{
                                cell.setCellValue(texto);
                            }
                            

                            CellStyle style = workbook.createCellStyle();
                            
                            if((i>=15 && i<=17) || (i>=26 && i<=31) || i==40){
                                style.setWrapText(true);
                                sheet.setColumnWidth(i, 50 * 256); 
                            }
                            if(i==24 || i==25 || i==38 || i==39){
                                Font font = workbook.createFont();
                                 if(resultado.equals("Bajo") || resultado.equals("IV")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, null);
                                    style.setFillForegroundColor(color);
                                    
                                }else if(resultado.equals("Medio") || resultado.equals("III")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 230, (byte) 153}, null);
                                    style.setFillForegroundColor(color);
                                }else if(resultado.equals("Alto") || resultado.equals("II")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 153, (byte) 102}, null);
                                    style.setFillForegroundColor(color);
                                }else if(resultado.equals("Muy Alto") || resultado.equals("I")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 192, (byte) 0, (byte) 0}, null);
                                    style.setFillForegroundColor(color);
                                    font.setColor(IndexedColors.WHITE.getIndex());
                                }
                                font.setBold(true);  // Establecer negrita
                                style.setFont(font);
                            }
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            cell.setCellStyle(style);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e);
                    }
                    i++;
                    

                }
                rowNum++;
            }
//            System.out.println("fin del for");
            int endRow = rowNum-1;
            // Aplica el filtro al rango especificado
            sheet.setAutoFilter(new CellRangeAddress(startRow, endRow, startColumn, endColumn));

            fis.close();
            FileOutputStream fos = new FileOutputStream(ROOT_DIR + "excel-plantilla" + File.separator + "consolidadoexcelcorona2.xlsx");
            workbook.write(fos);
            fos.close();
            workbook.close();
            
            InputStream fis2 = new FileInputStream(ROOT_DIR + "excel-plantilla" + File.separator + "consolidadoexcelcorona2.xlsx");

            String fileName="consolidadoexcelcorona.xlsx";
            Map<String, Object> map = FileUtil.saveInPathFS(fis2);
            //fis.close();
            String relativePath = (String) map.get(FileUtil.RELATIVE_PATH);
            Directorio dir = null;
            dir = new Directorio();
            dir.setEsDocumento(true);
            dir.setNombre(fileName);
            dir.setNivelAcceso("PUBLICO");
            dir.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            dir.setUsuario(super.getUsuarioRequestContext());
            dir.setDocumento(new Documento());
            dir.getDocumento().setRuta(relativePath);
            dir.getDocumento().setNombre(fileName);
            dir.getDocumento().setTamanio((long) map.get(FileUtil.FILE_SIZE));
            dir.getDocumento().setModulo(Modulo.valueOf("IPR"));
            
            directorioFacade.create(dir, "Consolidado");
             
            directorioFacade.actualizarModuloDir();
            
            Plantas plantabd = plantasFacade.find(list.get(0).getIdplantas());

            plantabd.setIdDocConsolidado(dir.getId().toString());
            plantabd.setFechaConsolidado(new Date());
            plantabd = plantasFacade.edit(plantabd);
            filterResponse.setData2(dir);
            System.out.println("fin");
            return Response.ok(filterResponse).build();
        } catch (Exception ex) {
            return Util.manageException(ex, ViewMatrizPeligrosREST.class);
        }
    
    }
    
}
