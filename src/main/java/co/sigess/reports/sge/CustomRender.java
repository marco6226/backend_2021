/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

import java.awt.Paint;
import org.jfree.chart.LegendItem;
import org.jfree.chart.renderer.category.BarRenderer;

/**
 *
 * @author fmoreno
 */

/**
 * A custom renderer that returns a different color for each item in a single
 * series.
 */
class CustomRenderer extends BarRenderer {

    /**
     * The colors.
     */
    private Paint[] colors;
    private boolean renderedSeries;

    /**
     * Creates a new renderer.
     *
     * @param colors the colors.
     */
    public CustomRenderer(final Paint[] colors, final boolean renderedSeries) {
        this.colors = colors;
        this.renderedSeries = renderedSeries;
    }
    
    /**
     * Creates a new renderer.
     *
     * @param colors the colors.
     */
    public CustomRenderer(final Paint[] colors) {
        this.colors = colors;
    }

    CustomRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Returns the paint for an item. Overrides the default behaviour inherited
     * from AbstractSeriesRenderer.
     *
     * @param row the series.
     * @param column the category.
     *
     * @return The item color.
     */
//    @Override
//    public Paint getItemPaint(final int row, final int column) {
//        if (this.renderedSeries) {
//            return this.colors[row];
//        } else {
//            return this.colors[column % this.colors.length];
//        }
//    }
//
//    @Override
//    public LegendItem getLegendItem(int datasetIndex, int series) {
//        if (this.renderedSeries) {
//            LegendItem legendItem = super.getLegendItem(datasetIndex, series); //To change body of generated methods, choose Tools | Templates.
//            legendItem.setFillPaint(this.colors[series]);
//            return legendItem;
//        } else {
//            return super.getLegendItem(datasetIndex, series);
//        }
//    }

    public Paint[] getColors() {
        return colors;
    }

    public void setColors(Paint[] colors) {
        this.colors = colors;
    }

    
}
