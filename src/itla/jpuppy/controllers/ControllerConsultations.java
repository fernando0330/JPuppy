package itla.jpuppy.controllers;

import itla.jpuppy.business.*;
import itla.jpuppy.datalayer.Consultations;
import itla.jpuppy.forms.EditConsultations;
import itla.jpuppy.forms.ManageConsultations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ControllerConsultations implements ActionListener {

    private ManageConsultations manageConsultations = null;
    //private List<Consultations> list = null;

    public ControllerConsultations(ManageConsultations mc) {
        this.manageConsultations = mc;
    }

    public ControllerConsultations() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals(manageConsultations.getActionCommandAdd())) {
            if (manageConsultations.getCbCustomerConsultations().getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Seleccione el cliente!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (manageConsultations.getCbPatientConsultations().getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Selecione el paciente!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (manageConsultations.getCbTypeConsultations().getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Selecione el tipo de consulta!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (manageConsultations.getTxtRemarkConsultations().getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "El campo observacion es requerido!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            System.out.println(new ModelPatients().searchAllPatientByName(manageConsultations.getCbPatientConsultations().getSelectedItem().toString()).get(0));
                            boolean insertObject;
                            String [ ] name = manageConsultations.getCbCustomerConsultations().getSelectedItem().toString().split("\\ ");
                            try {
                                insertObject = new ModelConsultations().insertObject(new Consultations(
                                        new Date(manageConsultations.getDateChooserBeginConsultations().getSelectedDate().getTimeInMillis()),
                                        new Date(manageConsultations.getDateChooserEndConsultations().getSelectedDate().getTimeInMillis()),
                                        new ModelCustomers().getCustomer(name[0]),
                                        new ModelPatients().getPatientsByName(manageConsultations.getCbPatientConsultations().getSelectedItem().toString()),
                                        manageConsultations.getTxtRemarkConsultations().getText(),
                                        new ModelAppointments().getSpecificAppointments(manageConsultations.getCbPatientConsultations().getSelectedItem().toString()),
                                        manageConsultations.getCbTypeConsultations().getSelectedItem().toString()));
                            } catch (Exception ecp) {
                                //ecp.printStackTrace();
                                insertObject = false;
                            }
                            if (insertObject) {
                                //Llenar el JTable de los datos existentes

                                Object[] nuevo = {manageConsultations.getCbTypeConsultations().getSelectedItem().toString(), manageConsultations.getCbCustomerConsultations().getSelectedItem().toString(), manageConsultations.getCbPatientConsultations().getSelectedItem().toString()};
                                //System.out.println(value.getBreedsName());
                                this.addToTable(nuevo);

                                JOptionPane.showMessageDialog(null, "La consulta fue agregada exitosamente!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "La consulta no pudo ser agregada !", "Informacion", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        } else if (comando.equals(manageConsultations.getActionCommandRemove())) {
            int delets = 0;
            boolean status = false;
            for (int b = 0; b < manageConsultations.getList().size(); b++) {
                Boolean test1 = (Boolean) manageConsultations.getTableConsultations().getValueAt(b, 4);
                if (test1 == null) {
                    status = false;
                    continue;
                } else if (test1 == true) {
                    status = true;
                    break;
                } else {
                    status = false;
                    continue;
                }
            }
            if (status == true) {
                if (JOptionPane.showConfirmDialog(null, "¿Desea realmente borrar esta(s) consulta(s)?", "Eliminar consulta:", JOptionPane.YES_NO_OPTION) == 0) {
                    for (int a = 0; a < manageConsultations.getList().size(); a++) {
                        Object val = manageConsultations.getTableConsultations().getValueAt(a, 4);
                        if (val instanceof Boolean) {
                            Boolean test = (Boolean) val;
                            if (test == true) {
                                //System.out.println(new ModelConsultations().getConsultationsByID(Long.parseLong(String.valueOf(manageConsultations.getTableConsultations().getValueAt(a, 0)))).getRemark());;
                                //Elimina la consulta
                                if (new ModelConsultations().deleteObject(new ModelConsultations().getConsultationsByID(Long.parseLong(String.valueOf(manageConsultations.getTableConsultations().getValueAt(a, 0)))))) {
                                    delets = delets + 1;
                                    //manageConsultations.getTableConsultations().remove(a);

                                    //.fireTableRowsDeleted(a, a);
                                }

//                                //System.out.println(manageSpecies.getJTableBreeds().getValueAt(a, 0).toString() +" - "+ manageSpecies.getJTableBreeds().getValueAt(a, 2).toString() +" - "+ manageSpecies.getJTableBreeds().getValueAt(a, 3).toString());
//                                breeds = queryManager.searchBreeds(manageSpecies.getJTableBreeds().getValueAt(a, 0).toString(), String.valueOf(manageSpecies.getJTableBreeds().getValueAt(a, 2)));
//                                //, Double.valueOf(String.valueOf(manageSpecies.getJTableBreeds().getValueAt(a, 2)))
//                                //System.out.println(breeds.getBreedsId());
//                                listBreeds.add(breeds);
                            }
                        }
                    }
                    if (delets > 0) {
                        String MSG = delets + " consulta(s) eliminada(s) exitosamente!";
                        JOptionPane.showMessageDialog(null, MSG, "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Favor selecciona las consultas que deseas eliminar!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (comando.equals(manageConsultations.getActionCommandUpd())) {
            //System.out.println();
            if (manageConsultations.getTableConsultations().getSelectedRow() == -1 || manageConsultations.getTableConsultations().getSelectedRows().length > 1) {
                JOptionPane.showMessageDialog(null, "Favor selecciona la consulta que sera editada!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Object[] values = {
                    manageConsultations.getTableConsultations().getValueAt(manageConsultations.getTableConsultations().getSelectedRow(), 1), 
                    manageConsultations.getTableConsultations().getValueAt(manageConsultations.getTableConsultations().getSelectedRow(), 2), 
                    manageConsultations.getTableConsultations().getValueAt(manageConsultations.getTableConsultations().getSelectedRow(), 3), 
                    new ModelConsultations().getConsultationsByID(Long.parseLong(String.valueOf(manageConsultations.getTableConsultations().getValueAt(manageConsultations.getTableConsultations().getSelectedRow(), 0)))).getRemark()
                };
                new EditConsultations(null, true, manageConsultations.getTableConsultations().getValueAt(manageConsultations.getTableConsultations().getSelectedRow(), 0), values).showFrame();
            }

        }
        else if (comando.equals(manageConsultations.getActionCommandCancel())){
            manageConsultations.closeFrame();
        }
    }

    public void addToTable(Object[] nuevo) {
        DefaultTableModel temp = (DefaultTableModel) manageConsultations.getTableConsultations().getModel();
        temp.addRow(nuevo);
    }
}