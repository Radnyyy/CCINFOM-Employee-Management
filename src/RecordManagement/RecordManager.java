/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package RecordManagement;

/**
 *
 * @author user
 */
public interface RecordManager {
    public void add();
    public void promptEdit();
    public void edit(int id, String field, Object newVal);
    public void delete();
    public void view();
    public void view(int id);
}

