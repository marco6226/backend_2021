/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.restful;

/**
 *
 * @author fmoreno
 */
public class FilterResponse {

    private long count;
    private Object data;
    private Object data2;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public Object getData2() {
        return data2;
    }

    public void setData2(Object data) {
        this.data2 = data;
    }
}
