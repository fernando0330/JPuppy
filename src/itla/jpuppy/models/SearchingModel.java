package itla.jpuppy.models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jordhano
 */
public class SearchingModel<T> extends AbstractTableModel {
    
    private String[]heads = null;
    private List<T> elements = null;
    private SearchingControl search = null;

    public SearchingModel(String [] heads, SearchingControl search) {
        this.heads = heads;
        this.search = search;
        elements = new ArrayList<T>();
    }
    
    @Override
    public String getColumnName(int x) {
        return heads[x];
    }
  
    @Override
    public int getRowCount() {
        return elements.size();
    }

    @Override
    public int getColumnCount() {
       return heads.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       return search.getValueAt(elements.get(rowIndex), columnIndex);
    }
    
}
