package edu.khai.k105.hydrosystem;

import edu.khai.k105.hydrosystem.graph.GraphModel;
import edu.khai.k105.hydrosystem.graph.GraphEditor;
import edu.khai.k105.hydrosystem.graph.GraphPoint;
import edu.khai.k105.hydrosystem.graph.GraphSeries;
import edu.khai.k105.hydrosystem.project.ProjectWindow;

import javax.swing.*;

public class GUI implements Runnable {

    private ProjectWindow projectWindow;

    @Override
    public void run() {
        projectWindow = new ProjectWindow();
        createGraphEditor();
    }

    private void createGraphEditor() {
        JFrame f = new JFrame ("Редактируем график");
        GraphModel graph = new GraphModel();
        GraphSeries series = new GraphSeries();
        series.addPoint(new GraphPoint(50, 50));
        series.addPoint(new GraphPoint(100, 100));
        series.addPoint(new GraphPoint(150, 50));
        series.addPoint(new GraphPoint(200, 100));
        graph.addSeries(series);
        GraphEditor graphEditor = new GraphEditor(graph);
        f.setContentPane(graphEditor.getContentPane());
        try {
//          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(f);
        }
        catch (Exception e){
            System.out.println("Ошибка при загрузке Metal-Look-And-Feel");
        }

        // Ранее практиковалось следующее: создавался listener и регистрировался
        // на экземпляре главного окна, который реагировал на windowClosing()
        // принудительной остановкой виртуальной машины вызовом System.exit()
        // Теперь же есть более "правильный" способ задав реакцию на закрытие окна.
        // Данный способ уничтожает текущее окно, но не останавливает приложение. Тем
        // самым приложение будет работать пока не будут закрыты все окна.

        f.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

        // однако можно задать и так:
        //            f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        // Добавляем на панель окна нередактируемый компонент с текстом.

        //f.getContentPane().add (new JLabel("Hello, World!")); - старый стиль
        //f.add(new JLabel("Hello World"));

        f.pack();
        f.setLocationRelativeTo(null);
        // Показать окно

        f.setVisible(true);
    }

}
