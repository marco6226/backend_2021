/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

/**
 *
 * @author fmoreno
 */
public class ReportDataSource {

    private String[] headers;
    private Object[] values;

    public ReportDataSource(String[] headers, Object[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Valor null no permitido");
        }
        if (values.length > 0 && headers.length != ((Object[]) values[0]).length) {
            throw new IllegalArgumentException("El número de columnas de las cabezeras no coincide con el de los valores");
        }
        this.headers = headers;
        this.values = values;
    }

    public Object getValue(int index, String header) {
        if (header == null) {
            return null;
        }
        for (int i = 0; i < headers.length; i++) {
            if (header.equals(headers[i])) {
                return ((Object[]) values[index])[i];
            }
        }
        return null;
    }

//    public <T> T getValue(String header, T clase) {
//        for (int i = 0; i < headers.length; i++) {
//            if (header.equals(headers[i])) {
//                return (T) values[i];
//            }
//        }
//        return null;
//    }

    public String[] getHeaders() {
        return headers;
    }

//    public void setHeaders(String[] headers) {
//        if (values != null && values.length != headers.length) {
//            throw new IllegalArgumentException("El número de columnas de las cabezeras no coincide con el de los valores");
//        }
//        this.headers = headers;
//    }
    public Object[] getValues() {
        return values;
    }

//    public void setValues(Object[] values) {
//        if (headers != null && values.length != headers.length) {
//            throw new IllegalArgumentException("El número de columnas de las cabezeras no coincide con el de los valores");
//        }
//        this.values = values;
//    }
}
