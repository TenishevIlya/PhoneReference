package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PhoneRef extends JFrame{
    JTextField fldFio = new JTextField(25);
    JTextField fldPhone = new JTextField(25);
    JTextField fldCnt = new JTextField(4);
    ArrayList<String> temp = new ArrayList<>();
    public int countUsers = 0;

    public PhoneRef() {
        super("Телефонный справочник");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {

        }
        setSize(450, 200);
        Container c = getContentPane();

        // Центральная панель
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        JLabel aLabel = new JLabel("Фамилия ");
        centerPanel.add(aLabel);
        centerPanel.add(fldFio);
        aLabel = new JLabel("Телефон ");
        centerPanel.add(aLabel);
        centerPanel.add(fldPhone);
        JButton btnAdd = new JButton("Добавить");
        centerPanel.add(btnAdd);
        c.add(centerPanel, BorderLayout.CENTER);
        /*JButton btnPrint = new JButton("Печатать");
        centerPanel.add(btnPrint);
        c.add(centerPanel, BorderLayout.CENTER);*/
        JButton btnDelete = new JButton("Удалить");
        centerPanel.add(btnDelete);
        c.add(centerPanel, BorderLayout.CENTER);

        // Нижняя панель
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        aLabel = new JLabel("Количество записей ");
        statusPanel.add(aLabel);
        fldCnt.setEnabled(false);
        statusPanel.add(fldCnt);
        JButton btnPrint = new JButton("Печатать");
        statusPanel.add(btnPrint);
        c.add(statusPanel, BorderLayout.SOUTH);
        JButton btnDeleteAll = new JButton("Удалить все записи");
        statusPanel.add(btnDeleteAll);
        c.add(statusPanel, BorderLayout.SOUTH);

        try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))){  // Создает буферный поток ввода символов который использует размер буфера по умолчанию.
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                temp.add(sCurrentLine);
            }
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }

        // Listener'ы полей и кнопок
        btnAdd.addActionListener(e -> {
          if (fldFio.getText().equals("") || fldPhone.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Заполните пустые поля");
          }
          else if ((fldFio.getText().matches("^[А-Яа-я]+$")) &&
                  (fldPhone.getText().matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$"))){
              temp.add(fldFio.getText() + " " + fldPhone.getText());
              countUsers = temp.size();
              fldCnt.setText(Integer.toString(countUsers));
              fldPhone.setText("");
              fldFio.setText("");
          }
          else {
            JOptionPane.showMessageDialog(null, "Введите данные корректно");
          }
        });

        btnPrint.addActionListener(e -> {
          try (FileWriter fw = new FileWriter("file.txt")){
            Collections.sort(temp);
            for (int i = 0; i < temp.size(); i++){
              fw.write(temp.get(i));
              fw.write(System.lineSeparator());
            }
            fw.flush();
          }
          catch (FileNotFoundException e1) {
              e1.printStackTrace();
          }
          catch (IOException e1) {
              e1.printStackTrace();
          }
          fldPhone.setText("");
          fldFio.setText("");
        });

        btnDelete.addActionListener(e ->{
          int count = 0;
          int index = 0;
          temp.clear();
          try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))){
                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    temp.add(sCurrentLine);
                }
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
          for (int i = 0; i < temp.size(); i++){
            if (temp.get(i).indexOf(fldFio.getText()) != -1){
              count++;
              index = i;
            }
          }
            if (count == 1){
              temp.remove(temp.get(index));
                try (FileWriter fw = new FileWriter("file.txt")){
                    Collections.sort(temp);
                    for (int i = 0; i < temp.size(); i++){
                        fw.write(temp.get(i));
                        fw.write(System.lineSeparator());
                    }
                    fw.flush();
                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Номер удален");
            }
            else{
                JOptionPane.showMessageDialog(null, "Такого человека нет в записной книге");
            }
            fldPhone.setText("");
            fldFio.setText("");
            fldCnt.setText(Integer.toString(temp.size()));
        });

        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }
        };

        addWindowListener(wndCloser);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PhoneRef();
    }

}
