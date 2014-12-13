package edu.khai.k105.hydrosystem;

import javax.swing.*;

public class GUI implements Runnable {

    @Override
    public void run() {


        // Создаем окно с заголовком "Hello, World!"

        JFrame f = new JFrame ("Hello, World!");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
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
        f.add(new JLabel("Hello World"));

        // pack() "упаковывает" окно до оптимального размера, рассчитанного на основании размеров
        // всех расположенных в нем компонентов.

        f.pack();

        // Показать окно

        f.setVisible(true);
    }

}
