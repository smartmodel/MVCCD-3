package utilities.window.editor.shuttle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
     * @author Dawid Kunert
     * @author Marcin Kunert
     */
    public class ShuttlePanel<T> extends JPanel implements ActionListener {

        private ShuttleDialog sdh;

        private static final long serialVersionUID = 1L;

        public static final int RIGHT_BUTTON = 1;
        public static final int LEFT_BUTTON_ID = 2;
        public static final int ALL_RIGHT_BUTTON_ID = 3;
        public static final int ALL_LEFT_BUTTON_ID = 4;

        public static final int LEFT_TABLE_ID = 1;
        public static final int RIGHT_TABLE_ID = 2;

        // view
        private JList<T> leftList;
        private JList<T> rightList;
        private JButton singleRight;
        private JButton singleLeft;
        private JButton allRight;
        private JButton allLeft;

        // listeners
        private List<OnListItemClickListener<T>> listItemClickListeners;
        private List<OnItemMoveButtonListener<T>> itemMoveButtonListeners;


        public ShuttlePanel() {
            this.setLayout(new BorderLayout(10,10));

            initListeners();


            leftList = new JList<T>();
            leftList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    fireListItemClickedEvent(leftList.getSelectedValue(), e.getClickCount(), LEFT_TABLE_ID);
                }
            });

            Dimension listDimension = new Dimension (200,80);
            JScrollPane leftScrollPane = new JScrollPane(leftList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            leftScrollPane.setPreferredSize(listDimension);

            rightList = new JList<T>();
            rightList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    fireListItemClickedEvent(rightList.getSelectedValue(), e.getClickCount(), RIGHT_TABLE_ID);
                }
            });

            JScrollPane rightScrollPane = new JScrollPane(rightList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            rightScrollPane.setPreferredSize(listDimension);

            add(leftScrollPane,BorderLayout.WEST);

            add(rightScrollPane, BorderLayout.EAST);


            Box cde = Box.createVerticalBox();
            add(cde, BorderLayout.CENTER);

            singleRight = new JButton(">");
            singleRight.addActionListener(this);
            cde.add(singleRight);

            allRight = new JButton(">>");
            allRight.addActionListener(this);
            cde.add(allRight);

            singleLeft = new JButton("<");
            singleLeft.addActionListener(this);
            cde.add(singleLeft);


            allLeft = new JButton("<<");
            allLeft.addActionListener(this);
            cde.add(allLeft);
        }


        public ShuttleDialog getSdh() {
            return sdh;
        }

        private void initListeners() {
            listItemClickListeners = new ArrayList<>();
            itemMoveButtonListeners = new ArrayList<>();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();

            int buttonId = 0;
            if (source == singleRight) {
                buttonId = RIGHT_BUTTON;
            }

            if (source == singleLeft) {
                buttonId = LEFT_BUTTON_ID;
            }

            if (source == allRight) {
                buttonId = ALL_RIGHT_BUTTON_ID;
            }

            if (source == allLeft) {
                buttonId = ALL_LEFT_BUTTON_ID;
            }

            fireItemMoveButtonPressedEvent(buttonId, leftList.getSelectedValuesList(), rightList.getSelectedValuesList());
        }

        protected void addOnListItemClickListener(OnListItemClickListener<T> listener) {
            listItemClickListeners.add(listener);
        }

        protected void addOnItemMoveButtonListener(OnItemMoveButtonListener<T> listener) {
            itemMoveButtonListeners.add(listener);
        }

        private void fireListItemClickedEvent(T elementClicked, int count, int tableId) {
            for (OnListItemClickListener<T> lisener : listItemClickListeners) {
                lisener.listItemClicked(elementClicked, count, tableId);
            }
        }

        private void fireItemMoveButtonPressedEvent(int buttonId, List<T> selectedLeftTableElements, List<T> selectedRightTableElements) {
            for (OnItemMoveButtonListener<T> lisener : itemMoveButtonListeners) {
                lisener.itemMoveButtonPressed(buttonId, selectedLeftTableElements, selectedRightTableElements);
            }
        }

        @SuppressWarnings("unchecked")
        protected void refreshData(ArrayList<T> leftTableElements, ArrayList<T> rightTableElements) {
            leftList.setListData((T[]) leftTableElements.toArray());
            rightList.setListData((T[]) rightTableElements.toArray());
        }

        public interface OnItemMoveButtonListener<T> {
            void itemMoveButtonPressed(int buttonId, List<T> selectedLeftTableElements, List<T> selectedRightTableElements);
        }

        public interface OnListItemClickListener<T> {
            void listItemClicked(T elementClicked, int count, int tableId);
        }



    }
