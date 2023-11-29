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
import co.sigess.entities.ipr.MatrizPeligrosLog;
import co.sigess.facade.ado.DirectorioFacade;
import co.sigess.facade.emp.AreaFacade;
import co.sigess.facade.emp.PlantasFacade;
import co.sigess.facade.ipr.MatrizPeligrosLogFacade;
import co.sigess.restful.FilterQuery;
import co.sigess.restful.FilterResponse;
import co.sigess.restful.ServiceREST;
import co.sigess.restful.emp.sfirmaREST;
import co.sigess.restful.ipr.MatrizPeligrosREST;
import co.sigess.restful.security.Secured;
import co.sigess.util.FileUtil;
import static co.sigess.util.FileUtil.ROOT_DIR;
import co.sigess.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
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
@Path("matrizPlog")
public class MatrizPeligrosLogREST extends ServiceREST{
    
    @EJB
    private  MatrizPeligrosLogFacade matrizPeligrosLogFacade;
    
    @EJB
    private  DirectorioFacade directorioFacade;
    
    @EJB
    private  PlantasFacade plantasFacade;
    
    @EJB
    private  AreaFacade areaFacade;
    
    public MatrizPeligrosLogREST() {
        super(MatrizPeligrosLogFacade.class);
    }
    
    @GET
    @Path("empresaId")
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response findEmpresa() {
        try {
            List<MatrizPeligrosLog> list = matrizPeligrosLogFacade.findForEmp(getEmpresaIdRequestContext());
            return Response.ok(list).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLogREST.class);
        }
    }
    
    @POST
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(MatrizPeligrosLog matrizPeligrosLog) {
        try {
            matrizPeligrosLog.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligrosLog = ((MatrizPeligrosLogFacade) beanInstance).create(matrizPeligrosLog);
            return Response.ok(matrizPeligrosLog).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLog.class);
        }
    }
    
    @PUT
    @Secured(validarPermiso = false)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response update(MatrizPeligrosLog matrizPeligrosLog) {
        try {
            matrizPeligrosLog.setEmpresa(new Empresa(super.getEmpresaIdRequestContext()));
            matrizPeligrosLog = ((MatrizPeligrosLogFacade) beanInstance).edit(matrizPeligrosLog);
            return Response.ok(matrizPeligrosLog).build();
        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosLogREST.class);
        }
    }
    
    @GET
    @Path("mpRegistroFilter")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findFirmWithFilter(@BeanParam FilterQuery filterQuery){
        try {
            if(filterQuery == null){
                filterQuery = new FilterQuery();
            }
            long numRows = filterQuery.isCount() ? matrizPeligrosLogFacade.countWithFilter(filterQuery) : -1;
            List list = matrizPeligrosLogFacade.findWithFilter(filterQuery);
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            filterResponse.setCount(numRows);
            return Response.ok(filterResponse).build();
        } catch (Exception e) {
            return Util.manageException(e, sfirmaREST.class);
        }
    }
    
    @GET
    @Path("mpExcelHistorico")
    @Secured(requiereEmpresaId = false)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response mpExcelHistorico(@BeanParam FilterQuery filterQuery){
        Gson gson = new Gson();
        JsonObject data=null;
        JsonObject data2=null;
        JsonObject dataBack=null;
        JsonObject dataNext=null;
        JsonObject dataBack2=null;
        JsonObject dataNext2=null;
        List<JsonObject> listPlanAccion2=new ArrayList<JsonObject>();
        List<JsonObject> listPlanAccionNext2=new ArrayList<JsonObject>();
        List<JsonObject> listPlanAccionBack2=new ArrayList<JsonObject>();
        try {
            
            List<MatrizPeligrosLog> list = matrizPeligrosLogFacade.findForEmp(getEmpresaIdRequestContext());
            
            FilterResponse filterResponse = new FilterResponse();
            filterResponse.setData(list);
            
            //FileInputStream fis = new FileInputStream(Recursos.EXCEL_CONSOLIDADO_CORONA.getRuta());
            InputStream fis = new FileInputStream(ROOT_DIR + "excel-plantilla" + File.separator + "historicoexcelcorona.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            int rowNum=5;
            
            JsonArray listPlanAccion = null;
            JsonArray listPlanAccionNext = null;
            JsonArray listPlanAccionBack = null;
            String descripcionEliminacion="";
            String descripcionSustitucion="";
            String descripcionIngenieria="";
            String descripcionAdministrativos="";
            String descripcionEquipos="";
            Integer contEliminacion=0;
            Integer contSustitucion=0;
            Integer contIngenieria=0;
            Integer contAdministrativos=0;
            Integer contEquipos=0;
            
            String descripcionIngenieriaEx="";
            String descripcionAdministrativosEx="";
            String descripcionEquiposEx="";
            Integer contIngenieriaEx=0;
            Integer contAdministrativosEx=0;
            Integer contEquiposEx=0;
            
            Integer cont1=0;
            Integer cont2=0;
            Integer cont1Next=0;
            Integer cont2Next=0;
            Integer cont1Back=0;
            Integer cont2Back=0;
            
            String estado="";
            
            int startRow = 4; // Fila 4 (índice 3)
            int startColumn = 0; // Columna A (índice 0)
            int endColumn = 48; // Columna AT (índice 45)
            
            int index=0;
            int size =list.size();

            //ubicación y planta
            Row row1 = sheet.createRow(1);
            Cell cell1 = row1.createCell(2);
            cell1.setCellValue("Ubicación:");
            cell1 = row1.createCell(3);
            List<Area> listArea= (List<Area>) areaFacade.findById(Integer.parseInt(list.get(0).getPlantas().getId_division()));
            cell1.setCellValue(listArea.get(0).getNombre());
            cell1 = row1.createCell(5);
            cell1.setCellValue("Planta:");
            cell1 = row1.createCell(6);
            cell1.setCellValue(list.get(0).getPlantas().getNombre());
            
            for (MatrizPeligrosLog element : list) {
                Row row = sheet.createRow(rowNum);
                System.out.println("Index: "+index);
                
                for(int i=0;i<49;i++){
                    Cell cell = row.createCell(i);
                    CellStyle style = workbook.createCellStyle();
                    switch(i){
                        case 0:
                            cell.setCellValue(element.getIdriesgo().intValue());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 1:
                            cell.setCellValue(element.getArea().getNombre());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);

                            if(index==0 && index <size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getArea().getNombre().equals(list.get(index+1).getArea().getNombre())){
                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getArea().getNombre().equals(list.get(index-1).getArea().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getArea().getNombre().equals(list.get(index+1).getArea().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getArea().getNombre().equals(list.get(index-1).getArea().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            
                            break;
                        case 2:
                            cell.setCellValue(element.getProceso().getNombre());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            if(index==0 && index <size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getProceso().getNombre().equals(list.get(index+1).getProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getProceso().getNombre().equals(list.get(index-1).getProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getProceso().getNombre().equals(list.get(index+1).getProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getProceso().getNombre().equals(list.get(index-1).getProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 3:
                            cell.setCellValue(element.getSubProceso().getNombre());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getSubProceso().getNombre().equals(list.get(index+1).getSubProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getSubProceso().getNombre().equals(list.get(index-1).getSubProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!list.get(index).getSubProceso().getNombre().equals(list.get(index+1).getSubProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!list.get(index).getSubProceso().getNombre().equals(list.get(index-1).getSubProceso().getNombre())){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 4:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            if (!data.get("Actividades").isJsonNull()) {
                                cell.setCellValue(data.get("Actividades").getAsString());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                    if(!dataNext.get("Actividades").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(!data.get("Actividades").getAsString().equals(dataNext.get("Actividades").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                    if(!dataBack.get("Actividades").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("Actividades").getAsString().equals(dataBack.get("Actividades").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("Actividades").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo()) && !dataNext.get("Actividades").isJsonNull()){
                                            if(!data.get("Actividades").getAsString().equals(dataNext.get("Actividades").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                    if(!dataBack.get("Actividades").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("Actividades").getAsString().equals(dataBack.get("Actividades").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }else{
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                    if(!dataNext.isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo()) && !dataNext.get("Actividades").isJsonNull()){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                    if(!dataBack.isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo()) && !dataBack.get("Actividades").isJsonNull()){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo()) && !dataNext.get("Actividades").isJsonNull()){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                    if(!dataBack.isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo()) && !dataBack.get("Actividades").isJsonNull()){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 5:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            cell.setCellValue((data.get("Rutinaria").getAsInt()==1)?"Sí":"No");
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Rutinaria").getAsInt()!=dataNext.get("Rutinaria").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Rutinaria").getAsInt()!=dataBack.get("Rutinaria").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Rutinaria").getAsInt()!=dataNext.get("Rutinaria").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Rutinaria").getAsInt()!=dataBack.get("Rutinaria").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 6:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            cell.setCellValue(data.get("Propios").getAsInt());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Propios").getAsInt()!=dataNext.get("Propios").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Propios").getAsInt()!=dataBack.get("Propios").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Propios").getAsInt()!=dataNext.get("Propios").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Propios").getAsInt()!=dataBack.get("Propios").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 7:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            cell.setCellValue(data.get("Temporales").getAsInt());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Temporales").getAsInt()!=dataNext.get("Temporales").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Temporales").getAsInt()!=dataBack.get("Temporales").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Temporales").getAsInt()!=dataNext.get("Temporales").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Temporales").getAsInt()!=dataBack.get("Temporales").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            
                            break; 
                        case 8:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            cell.setCellValue(data.get("Contratistas").getAsInt());
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Contratistas").getAsInt()!=dataNext.get("Contratistas").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Contratistas").getAsInt()!=dataBack.get("Contratistas").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(data.get("Contratistas").getAsInt()!=dataNext.get("Contratistas").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(data.get("Contratistas").getAsInt()!=dataBack.get("Contratistas").getAsInt()){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            
                            break;
                        case 9:
                            data = gson.fromJson(element.getGeneralInf(), JsonObject.class);
                            int sumData=data.get("Propios").getAsInt()+data.get("Temporales").getAsInt()+data.get("Contratistas").getAsInt();
                            cell.setCellValue(sumData);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            if(index==0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                int sumDataNext=dataNext.get("Propios").getAsInt()+dataNext.get("Temporales").getAsInt()+dataNext.get("Contratistas").getAsInt();
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(sumData != sumDataNext){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size -1){
                                dataNext = gson.fromJson(list.get(index+1).getGeneralInf(), JsonObject.class);
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                int sumDataNext=dataNext.get("Propios").getAsInt()+dataNext.get("Temporales").getAsInt()+dataNext.get("Contratistas").getAsInt();
                                int sumDataBack=dataBack.get("Propios").getAsInt()+dataBack.get("Temporales").getAsInt()+dataBack.get("Contratistas").getAsInt();
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(sumData != sumDataBack){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(sumData != sumDataNext){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index == size-1){
                                dataBack = gson.fromJson(list.get(index-1).getGeneralInf(), JsonObject.class);
                                int sumDataBack=dataBack.get("Propios").getAsInt()+dataBack.get("Temporales").getAsInt()+dataBack.get("Contratistas").getAsInt();
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(sumData != sumDataBack){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            
                            break;
                        case 10:
                            data = gson.fromJson(element.getPeligro(), JsonObject.class);
                            if (!data.get("Peligro").isJsonNull()) {
                                data2 = gson.fromJson(data.get("Peligro"), JsonObject.class);
                                if (!data2.get("nombre").isJsonNull()) {
                                    cell.setCellValue(data2.get("nombre").getAsString());
                                    style.setAlignment(HorizontalAlignment.CENTER);
                                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                                    
                                    if(index==0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        if (!dataNext.get("Peligro").isJsonNull()){
                                            dataNext2 = gson.fromJson(dataNext.get("Peligro"), JsonObject.class);
                                            if (!dataNext2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataNext2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(index>0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                        if (!dataBack.get("Peligro").isJsonNull()){
                                            dataBack2 = gson.fromJson(dataBack.get("Peligro"), JsonObject.class);
                                            if (!dataBack2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataBack2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                        if (!dataNext.get("Peligro").isJsonNull()){
                                            dataNext2 = gson.fromJson(dataNext.get("Peligro"), JsonObject.class);
                                            if (!dataNext2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataNext2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(index == size-1){
                                        dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                        if (!dataBack.get("Peligro").isJsonNull()){
                                            dataBack2 = gson.fromJson(dataBack.get("Peligro"), JsonObject.class);
                                            if (!dataBack2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataBack2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 11:
                            data = gson.fromJson(element.getPeligro(), JsonObject.class);
                            if (!data.get("DescripcionPeligro").isJsonNull()) {
                                data2 = gson.fromJson(data.get("DescripcionPeligro"), JsonObject.class);
                                if (!data2.get("nombre").isJsonNull()) {
                                    cell.setCellValue(data2.get("nombre").getAsString());
                                    style.setAlignment(HorizontalAlignment.CENTER);
                                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                                    
                                    if(index==0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        if (!dataNext.get("DescripcionPeligro").isJsonNull()){
                                            dataNext2 = gson.fromJson(dataNext.get("DescripcionPeligro"), JsonObject.class);
                                            if (!dataNext2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataNext2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(index>0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                        if (!dataBack.get("DescripcionPeligro").isJsonNull()){
                                            dataBack2 = gson.fromJson(dataBack.get("DescripcionPeligro"), JsonObject.class);
                                            if (!dataBack2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataBack2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                        if (!dataNext.get("DescripcionPeligro").isJsonNull()){
                                            dataNext2 = gson.fromJson(dataNext.get("DescripcionPeligro"), JsonObject.class);
                                            if (!dataNext2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataNext2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else if(index == size-1){
                                        dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                        if (!dataBack.get("DescripcionPeligro").isJsonNull()){
                                            dataBack2 = gson.fromJson(dataBack.get("DescripcionPeligro"), JsonObject.class);
                                            if (!dataBack2.get("nombre").isJsonNull()) {
                                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                                    if(!data2.get("nombre").getAsString().equals(dataBack2.get("nombre").getAsString())){
                                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                        style.setFillForegroundColor(color);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 12:
                            data = gson.fromJson(element.getPeligro(), JsonObject.class);
                            if (!data.get("FuenteGeneradora").isJsonNull()) {
                                cell.setCellValue(data.get("FuenteGeneradora").getAsString());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        if (!dataNext.get("FuenteGeneradora").isJsonNull()){
                                            if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                if(!data.get("FuenteGeneradora").getAsString().equals(dataNext.get("FuenteGeneradora").getAsString())){
                                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                    style.setFillForegroundColor(color);
                                                }
                                            }
                                            
                                        }
                                }
                                else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                    if (!dataBack.get("FuenteGeneradora").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("FuenteGeneradora").getAsString().equals(dataBack.get("FuenteGeneradora").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }

                                    }
                                    if (!dataNext.get("FuenteGeneradora").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(!data.get("FuenteGeneradora").getAsString().equals(dataNext.get("FuenteGeneradora").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }
                                }
                                else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                    if (!dataBack.get("FuenteGeneradora").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("FuenteGeneradora").getAsString().equals(dataBack.get("FuenteGeneradora").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 13:
                            data = gson.fromJson(element.getPeligro(), JsonObject.class);
                            if (!data.get("Efectos").isJsonNull()) {
                                cell.setCellValue(data.get("Efectos").getAsString());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                if(index==0 && index <size -1){
                                        dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                        if (!dataNext.get("Efectos").isJsonNull()){
                                            if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                                if(!data.get("Efectos").getAsString().equals(dataNext.get("Efectos").getAsString())){
                                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                    style.setFillForegroundColor(color);
                                                }
                                            }
                                            
                                        }
                                }
                                else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getPeligro(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                    if (!dataBack.get("Efectos").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("Efectos").getAsString().equals(dataBack.get("Efectos").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }

                                    }
                                    if (!dataNext.get("Efectos").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(!data.get("Efectos").getAsString().equals(dataNext.get("Efectos").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }
                                }
                                else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getPeligro(), JsonObject.class);
                                    if (!dataBack.get("Efectos").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(!data.get("Efectos").getAsString().equals(dataBack.get("Efectos").getAsString())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case 14:
                            if(element.getControlesexistentes() !=null){
                                data = gson.fromJson(element.getControlesexistentes(), JsonObject.class);
                                if (!data.get("Ingenieria").isJsonNull()) {
                                    //cell.setCellValue(data.get("Ingenieria").getAsString());
                                    listPlanAccion = gson.fromJson(data.get("Ingenieria"), JsonArray.class);

                                    for(JsonElement planAccion : listPlanAccion){
                                        JsonObject pAccion = planAccion.getAsJsonObject();
                                        contIngenieriaEx++;
                                        descripcionIngenieriaEx+=contIngenieriaEx+". "+pAccion.get("descripcion").getAsString()+"\n";
                                    }
                                    
                                    cell.setCellValue(descripcionIngenieriaEx);
                                    style.setWrapText(true);
                                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                                    sheet.setColumnWidth(i, 40 * 256); 
                                }
                            }
                            break;
                        case 15:
                            if(element.getControlesexistentes()!=null){
                                data = gson.fromJson(element.getControlesexistentes(), JsonObject.class);
                                if (!data.get("Administrativos").isJsonNull()) {
                                    listPlanAccion = gson.fromJson(data.get("Administrativos"), JsonArray.class);

                                    for(JsonElement planAccion : listPlanAccion){
                                        JsonObject pAccion = planAccion.getAsJsonObject();
                                        contAdministrativosEx++;
                                        descripcionAdministrativosEx+=contAdministrativosEx+". "+pAccion.get("descripcion").getAsString()+"\n";
                                    }
                                    
                                    cell.setCellValue(descripcionAdministrativosEx);
                                    style.setWrapText(true);
                                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                                    sheet.setColumnWidth(i, 70 * 256); 
                                }
                            }
                            break;
                        case 16:
                            if(element.getControlesexistentes()!=null){
                                data = gson.fromJson(element.getControlesexistentes(), JsonObject.class);
                                if (!data.get("ElementosPro").isJsonNull()) {
                                    listPlanAccion = gson.fromJson(data.get("ElementosPro"), JsonArray.class);

                                    for(JsonElement planAccion : listPlanAccion){                                        
                                        JsonObject pAccion = planAccion.getAsJsonObject();
                                        contEquiposEx++;
                                        descripcionEquiposEx+=contEquiposEx+". "+pAccion.get("descripcion").getAsString()+"\n";
                                    }
                                    
                                    cell.setCellValue(descripcionEquiposEx);
                                    style.setWrapText(true);
                                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                                    sheet.setColumnWidth(i, 40 * 256); 
                                }
                            }
                            break; 
                        case 17:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("ND").isJsonNull()) {
                                cell.setCellValue(data.get("ND").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataNext.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataBack.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataNext.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != (dataBack.get("ND").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 18:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NE").isJsonNull()) {
                                cell.setCellValue(data.get("NE").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataNext.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataBack.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataNext.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != (dataBack.get("NE").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 19:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NP").isJsonNull()) {
                                cell.setCellValue(data.get("NP").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataBack.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != (dataBack.get("NP").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 20:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NP").isJsonNull()) {
                                Integer NP = data.get("NP").getAsInt();
                             
                                if(4>=NP && NP>=0){
                                    cell.setCellValue("Bajo");
                                }else if(8>=NP && NP>=6){
                                    cell.setCellValue("Medio");
                                }else if(20>=NP && NP>=10){
                                    cell.setCellValue("Alto");
                                }else if(40>=NP && NP>=24){
                                    cell.setCellValue("Muy Alto");
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataBack.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != (dataBack.get("NP").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 21:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NC").isJsonNull()) {
                                cell.setCellValue(data.get("NC").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataNext.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataBack.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataNext.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != (dataBack.get("NC").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 22:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                cell.setCellValue(data.get("NR").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataBack.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != (dataBack.get("NR").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 23:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                Integer NR = data.get("NR").getAsInt();
                                Font font = workbook.createFont();                                
                                if(20>=NR && NR>=0){
                                    cell.setCellValue("IV");

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, null);
                                    style.setFillForegroundColor(color);
                                    
                                }else if(120>=NR && NR>=40){
                                    cell.setCellValue("III");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 230, (byte) 153}, null);
                                    style.setFillForegroundColor(color);
                                }else if(500>=NR && NR>=150){
                                    cell.setCellValue("II");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 153, (byte) 102}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NR>=600){
                                    cell.setCellValue("I");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 192, (byte) 0, (byte) 0}, null);
                                    style.setFillForegroundColor(color);
                                    font.setColor(IndexedColors.WHITE.getIndex());
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                font.setBold(true);  // Establecer negrita
                                style.setFont(font);
                            }
                            break;
                        case 24:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NRCualitativo").isJsonNull()) {
                                Font font = workbook.createFont();
                                String NRCualitativo=data.get("NRCualitativo").getAsString();
                                cell.setCellValue(NRCualitativo);
                                
                                if(NRCualitativo.equals("Bajo")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, null);
                                    style.setFillForegroundColor(color);
                                    
                                }else if(NRCualitativo.equals("Medio")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 230, (byte) 153}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NRCualitativo.equals("Alto")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 153, (byte) 102}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NRCualitativo.equals("Muy Alto")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 192, (byte) 0, (byte) 0}, null);
                                    style.setFillForegroundColor(color);
                                    font.setColor(IndexedColors.WHITE.getIndex());
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                font.setBold(true);  // Establecer negrita
                                style.setFont(font);
                            }

                            break;
                        case 25:
                            data = gson.fromJson(element.getValoracionRiesgoInicial(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                Integer NR = data.get("NR").getAsInt();

                                if(20>=NR && NR>=0){
                                    cell.setCellValue("Mantenga los controles existentes");
                                }else if(120>=NR && NR>=40){
                                    cell.setCellValue("1. Mantenga los controles existentes \n 2. Identifique mejoras");
                                }else if(500>=NR && NR>=150){
                                    cell.setCellValue("1. Intervenga de inmediato \n 2. Implemente controles (existentes o adicionales) \n 3. Identifique desviaciones si existe");
                                }else if(NR>=600){
                                    cell.setCellValue("1. Suspenda la actividad. \n 2. intervenga de inmediato \n 3.Implemente controles. Intervenir es comunicar la situación a los diferentes responsables y ejecutores de la tarea (requiere reporte y gestión de acto y condiciones) Alto");
                                }
                                                          
                                style.setWrapText(true);  // Activar el ajuste de texto
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                // Establecer el ancho de la columna
                                sheet.setColumnWidth(i, 50 * 256); 
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataBack.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != (dataBack.get("NR").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 26://AQUI VOY
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            listPlanAccion2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccion, "Eliminación");
                            descripcionEliminacion="";
                            contEliminacion=0;
                            
                            for(JsonElement planAccion : listPlanAccion2){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("jerarquia").getAsString().equals("Eliminación")){
                                    contEliminacion++;
                                    descripcionEliminacion+=contEliminacion+". "+pAccion.get("descripcion").getAsString()+"\n";
                                }
                            }
                            cell.setCellValue(descripcionEliminacion);
                            
                            style.setWrapText(true);  // Activar el ajuste de texto
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            // Establecer el ancho de la columna
                            //sheet.setColumnWidth(i, 60 * 256);
                            
                            if(index==0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Eliminación");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Eliminación");
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Eliminación");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
           
                            }else if(index == size-1){
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Eliminación");
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            
                            break;
                        case 27:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            listPlanAccion2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccion, "Sustitución");
                            descripcionSustitucion="";
                            contSustitucion=0;

                            for(JsonElement planAccion : listPlanAccion2){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("jerarquia").getAsString().equals("Sustitución")){
                                    contSustitucion++;
                                    descripcionSustitucion+=contSustitucion+". "+pAccion.get("descripcion").getAsString()+"\n";
                                }
                            }
                            cell.setCellValue(descripcionSustitucion);
                            
                            style.setWrapText(true);  // Activar el ajuste de texto
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            // Establecer el ancho de la columna
                            //sheet.setColumnWidth(i, 60 * 256); 
                            
                            if(index==0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Sustitución");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Sustitución");
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Sustitución");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
           
                            }else if(index == size-1){
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Sustitución");
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break; 
                        case 28:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            listPlanAccion2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccion, "Control de ingeniería");
                            
                            descripcionIngenieria="";
                            contIngenieria=0;
                            
                            for(JsonElement planAccion : listPlanAccion2){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("jerarquia").getAsString().equals("Control de ingeniería")){
                                    contIngenieria++;
                                    descripcionIngenieria+=contIngenieria+". "+pAccion.get("descripcion").getAsString()+"\n";
                                }
                            }
                            
                            cell.setCellValue(descripcionIngenieria);
                            
                            style.setWrapText(true);  // Activar el ajuste de texto
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            // Establecer el ancho de la columna
                            sheet.setColumnWidth(i, 50 * 256); 
                            
                            if(index==0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Control de ingeniería");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Control de ingeniería");
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Control de ingeniería");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
           
                            }else if(index == size-1){
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Control de ingeniería");
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 29:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            listPlanAccion2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccion, "Controles administrativos");
                            
                            descripcionAdministrativos="";
                            contAdministrativos=0;
                            
                            for(JsonElement planAccion : listPlanAccion2){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("jerarquia").getAsString().equals("Controles administrativos")){
                                    contAdministrativos++;
                                    descripcionAdministrativos+=contAdministrativos+". "+pAccion.get("descripcion").getAsString()+"\n";
                                }
                            }
                            cell.setCellValue(descripcionAdministrativos);
                            
                            style.setWrapText(true);  // Activar el ajuste de texto
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            // Establecer el ancho de la columna
                            sheet.setColumnWidth(i, 60 * 256); 
                            
                            if(index==0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Controles administrativos");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Controles administrativos");
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Controles administrativos");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
           
                            }else if(index == size-1){
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Controles administrativos");
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 30:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            listPlanAccion2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccion, "Elementos de protección personal");
                            
                            descripcionEquipos="";
                            contEquipos=0;
                            
                            for(JsonElement planAccion : listPlanAccion2){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("jerarquia").getAsString().equals("Elementos de protección personal")){
                                    contEquipos++;
                                    descripcionEquipos+=contEquipos+". "+pAccion.get("descripcion").getAsString()+"\n";
                                }
                            }
                            cell.setCellValue(descripcionEquipos);
                            
                            style.setWrapText(true);  // Activar el ajuste de texto
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            // Establecer el ancho de la columna
                            sheet.setColumnWidth(i, 50 * 256); 
                            
                            if(index==0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Elementos de protección personal");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){                                        
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }else if(index>0 && index <size-1){
                                listPlanAccionNext = gson.fromJson(list.get(index+1).getPlanAccion(), JsonArray.class);
                                listPlanAccionNext2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionNext, "Elementos de protección personal");
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Elementos de protección personal");                                
                                if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionNext2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
           
                            }else if(index == size-1){
                                listPlanAccionBack = gson.fromJson(list.get(index-1).getPlanAccion(), JsonArray.class);
                                listPlanAccionBack2 = matrizPeligrosLogFacade.elementPlanAccion(listPlanAccionBack, "Elementos de protección personal");
                                if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                    if(!listPlanAccion2.equals(listPlanAccionBack2)){
                                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                        XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                        style.setFillForegroundColor(color);
                                    }
                                }
                            }
                            break;
                        case 31:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("ND").isJsonNull()) {
                                cell.setCellValue(data.get("ND").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataNext.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataBack.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != dataNext.get("ND").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("ND").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("ND").getAsInt() != (dataBack.get("ND").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 32:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NE").isJsonNull()) {
                                cell.setCellValue(data.get("NE").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataNext.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataBack.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != dataNext.get("NE").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NE").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NE").getAsInt() != (dataBack.get("NE").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 33:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NP").isJsonNull()) {
                                cell.setCellValue(data.get("NP").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataBack.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != (dataBack.get("NP").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 34:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NP").isJsonNull()) {
                                Integer NP = data.get("NP").getAsInt();
                             
                                if(4>=NP && NP>=0){
                                    cell.setCellValue("Bajo");
                                }else if(8>=NP && NP>=6){
                                    cell.setCellValue("Medio");
                                }else if(20>=NP && NP>=10){
                                    cell.setCellValue("Alto");
                                }else if(40>=NP && NP>=24){
                                    cell.setCellValue("Muy Alto");
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataBack.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != dataNext.get("NP").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NP").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NP").getAsInt() != (dataBack.get("NP").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 35:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NC").isJsonNull()) {
                                cell.setCellValue(data.get("NC").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataNext.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataBack.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != dataNext.get("NC").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NC").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NC").getAsInt() != (dataBack.get("NC").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 36:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                cell.setCellValue(data.get("NR").getAsInt());
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataBack.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != (dataBack.get("NR").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 37:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                Integer NR = data.get("NR").getAsInt();
                                Font font = workbook.createFont();                                
                                if(20>=NR && NR>=0){
                                    cell.setCellValue("IV");

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, null);
                                    style.setFillForegroundColor(color);
                                    
                                }else if(120>=NR && NR>=40){
                                    cell.setCellValue("III");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 230, (byte) 153}, null);
                                    style.setFillForegroundColor(color);
                                }else if(500>=NR && NR>=150){
                                    cell.setCellValue("II");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 153, (byte) 102}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NR>=600){
                                    cell.setCellValue("I");
                                    
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 192, (byte) 0, (byte) 0}, null);
                                    style.setFillForegroundColor(color);
                                    font.setColor(IndexedColors.WHITE.getIndex());
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                font.setBold(true);  // Establecer negrita
                                style.setFont(font);
                            }
                            break;
                        case 38:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NRCualitativo").isJsonNull()) {
                                Font font = workbook.createFont();
                                String NRCualitativo=data.get("NRCualitativo").getAsString();
                                cell.setCellValue(NRCualitativo);
                                
                                if(NRCualitativo.equals("Bajo")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 169, (byte) 208, (byte) 142}, null);
                                    style.setFillForegroundColor(color);
                                    
                                }else if(NRCualitativo.equals("Medio")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 230, (byte) 153}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NRCualitativo.equals("Alto")){

                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 153, (byte) 102}, null);
                                    style.setFillForegroundColor(color);
                                }else if(NRCualitativo.equals("Muy Alto")){
                                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                    XSSFColor color = new XSSFColor(new byte[]{(byte) 192, (byte) 0, (byte) 0}, null);
                                    style.setFillForegroundColor(color);
                                    font.setColor(IndexedColors.WHITE.getIndex());
                                }
                                style.setAlignment(HorizontalAlignment.CENTER);
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                
                                font.setBold(true);  // Establecer negrita
                                style.setFont(font);
                            }

                            break;
                        case 39:
                            data = gson.fromJson(element.getValoracionRiesgoResidual(), JsonObject.class);
                            if (!data.get("NR").isJsonNull()) {
                                Integer NR = data.get("NR").getAsInt();

                                if(20>=NR && NR>=0){
                                    cell.setCellValue("Mantenga los controles existentes");
                                }else if(120>=NR && NR>=40){
                                    cell.setCellValue("1. Mantenga los controles existentes \n 2. Identifique mejoras");
                                }else if(500>=NR && NR>=150){
                                    cell.setCellValue("1. Intervenga de inmediato \n 2. Implemente controles (existentes o adicionales) \n 3. Identifique desviaciones si existe");
                                }else if(NR>=600){
                                    cell.setCellValue("1. Suspenda la actividad. \n 2. intervenga de inmediato \n 3.Implemente controles. Intervenir es comunicar la situación a los diferentes responsables y ejecutores de la tarea (requiere reporte y gestión de acto y condiciones) Alto");
                                }
                                                          
                                style.setWrapText(true);  // Activar el ajuste de texto
                                style.setVerticalAlignment(VerticalAlignment.CENTER);
                                // Establecer el ancho de la columna
                                sheet.setColumnWidth(i, 50 * 256); 
                                
                                if(index==0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index>0 && index <size -1){
                                    dataNext = gson.fromJson(list.get(index+1).getValoracionRiesgoInicial(), JsonObject.class);
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataBack.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                    if(!dataNext.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != dataNext.get("NR").getAsInt()){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index+1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }else if(index == size-1){
                                    dataBack = gson.fromJson(list.get(index-1).getValoracionRiesgoInicial(), JsonObject.class);
                                    if(!dataBack.get("NR").isJsonNull()){
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            if(data.get("NR").getAsInt() != (dataBack.get("NR").getAsInt())){
                                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                                XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                                style.setFillForegroundColor(color);
                                            }
                                        }
                                    }else{
                                        if(list.get(index).getIdriesgo().equals(list.get(index-1).getIdriesgo())){
                                            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                            XSSFColor color = new XSSFColor(new byte[]{(byte) 255, (byte) 187, (byte) 0}, null);
                                            style.setFillForegroundColor(color);
                                        }
                                    }
                                }
                            }
                            break;
                        case 40:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            cont1=0;
                            cont2=0;
                            for(JsonElement planAccion : listPlanAccion){
                                cont1++;
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("estado").getAsString().equals("Ejecutado")){
                                    cont2++;
                                }
                            }
                            cell.setCellValue(cont2);
                            
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            
                            
                            break;
                        case 41:
                            cell.setCellValue(cont1);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 42:
                            if(cont1==0 && cont2==0){
                                cell.setCellValue("0%");
                            }else{
                                cell.setCellValue((cont2*100/cont1)+"%");
                            }
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break; 
                        case 43:
                            cell.setCellValue(0);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 44:
                            cell.setCellValue(0);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 45:
                            listPlanAccion = gson.fromJson(element.getPlanAccion(), JsonArray.class);
                            estado="Riesgo Vigente";
                            for(JsonElement planAccion : listPlanAccion){
                                JsonObject pAccion = planAccion.getAsJsonObject();
                                if(pAccion.get("estado").getAsString().equals("Ejecutado") && pAccion.get("jerarquia").getAsString().equals("Sustitución")){
                                    estado="Riesgo Sustituido";
                                }else if(pAccion.get("estado").getAsString().equals("Ejecutado") && pAccion.get("jerarquia").getAsString().equals("Eliminación")){
                                    estado="Riesgo Eliminado";
                                }
                            }
                            cell.setCellValue(estado);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            sheet.setColumnWidth(i, 30 * 256); 
                            break;    
                            
                        case 46:
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Puedes ajustar el formato como desees
                            String fechaFormateada = sdf.format(element.getFechaEdicion());
                            cell.setCellValue(fechaFormateada);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 47:                          
                            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM");
                            String meses = sdf1.format(element.getFechaEdicion());

                            cell.setCellValue(meses);

                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        case 48:
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(element.getFechaEdicion());
                            
                            int anio = calendar2.get(Calendar.YEAR);

                            cell.setCellValue(anio);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER);
                            break;
                        default:
                            break;
                            
                        
                    }
                    cell.setCellStyle(style);
                }
                rowNum++;
                index++;
            }
            
            int endRow = rowNum-1;  // Fila 35 (índice 34)
            // Aplica el filtro al rango especificado
            sheet.setAutoFilter(new CellRangeAddress(startRow, endRow, startColumn, endColumn));
            
            
            fis.close();
            FileOutputStream fos = new FileOutputStream(ROOT_DIR + "excel-plantilla" + File.separator + "historicoexcelcorona2.xlsx");
            workbook.write(fos);
            fos.close();
            workbook.close();
            
            
            InputStream fis2 = new FileInputStream(ROOT_DIR + "excel-plantilla" + File.separator + "historicoexcelcorona2.xlsx");

            String fileName="historicoexcelcorona.xlsx";
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
            
            directorioFacade.create(dir, "Historico");
             
            directorioFacade.actualizarModuloDir();
            
            Plantas plantabd = plantasFacade.find(list.get(0).getPlantas().getId());
            plantabd.setIdDocHistorico(dir.getId().toString());;
            plantabd.setFechaHistorico(new Date());
            plantabd = plantasFacade.edit(plantabd);
            filterResponse.setData2(dir);
            return Response.ok(filterResponse).build();
            

        } catch (Exception ex) {
            return Util.manageException(ex, MatrizPeligrosREST.class);
        }
    }
}
