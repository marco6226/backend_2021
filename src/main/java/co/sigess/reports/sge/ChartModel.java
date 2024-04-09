/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import co.sigess.entities.emp.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotAuthorizedException;

/**
 *
 * @author fmoreno
 */
public class ChartModel {

    private String title;
    private String datasource;
    private String type;
    private String valueKey;
    private String rowKey;
    private String columnKey;
    private int titleColor = Color.DARK_GRAY.getRGB();
    private int backgroundPlotColor = Color.WHITE.getRGB();
    private int rangeGridlineColor = Color.LIGHT_GRAY.getRGB();
    private String itemLabelFont = "SansSerif-PLAIN-14";
    private boolean verticalOrientation = true;
    private int width = 640;
    private int height = 480;
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getBackgroundPlotColor() {
        return backgroundPlotColor;
    }

    public void setBackgroundPlotColor(int backgroundPlotColor) {
        this.backgroundPlotColor = backgroundPlotColor;
    }

    public int getRangeGridlineColor() {
        return rangeGridlineColor;
    }

    public void setRangeGridlineColor(int rangeGridlineColor) {
        this.rangeGridlineColor = rangeGridlineColor;
    }

    public String getItemLabelFont() {
        return itemLabelFont;
    }

    public void setItemLabelFont(String itemLabelFont) {
        this.itemLabelFont = itemLabelFont;
    }

    public boolean isVerticalOrientation() {
        return verticalOrientation;
    }

    public void setVerticalOrientation(boolean verticalOrientation) {
        this.verticalOrientation = verticalOrientation;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public static ChartModel fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<ChartModel>() {
            });
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ChartModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        } catch (IOException ex) {
            Logger.getLogger(ChartModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotAuthorizedException("No Authorization");
        }
    }

}
