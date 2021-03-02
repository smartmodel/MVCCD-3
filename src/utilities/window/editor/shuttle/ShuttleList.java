package utilities.window.editor.shuttle;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

    //TODO javadoc
    /**
     * @author Dawid Kunert
     * @author Marcin Kunert
     */
    public class ShuttleList<T> extends JComponent {

        private static final long serialVersionUID = -2630410357962722533L;
        private ShuttlePanel<T> shuttlePanel;

        private ArrayList<T> leftInitialTableElements = new ArrayList<T>();
        private ArrayList<T> rightInitialTableElements = new ArrayList<T>();
        private ArrayList<T> leftTableElements = new ArrayList<T>();
        private ArrayList<T> rightTableElements = new ArrayList<T>();

        private ArrayList<T> resultat;

        //TODO javadoc
        public ShuttleList() {
            shuttlePanel = new ShuttlePanel<>();

            setLayout(new BorderLayout());

            addClickListener();
            addButtonListener();

            add(shuttlePanel, BorderLayout.CENTER);
        }

	/*
	public ShuttleList(Collection<T> elements) {
		this();
		addElements(elements);
	}
	*/

        private void addClickListener() {
            shuttlePanel.addOnListItemClickListener(new ShuttlePanel.OnListItemClickListener<T>() {

                @Override
                public void listItemClicked(T elementClicked, int count, int tableId) {

                    if (count == 2) {

                        if (tableId == ShuttlePanel.LEFT_TABLE_ID) {
                            moveElementToRight(elementClicked);
                        }

                        if (tableId == ShuttlePanel.RIGHT_TABLE_ID) {
                            moveElementToLeft(elementClicked);
                        }
                        refreshView();
                    }
                }
            });
        }

        private void moveElementToRight(T elem) {
            leftTableElements.remove(elem);
            rightTableElements.add(elem);
        }

        private void moveElementToLeft(T elem) {
            rightTableElements.remove(elem);
            leftTableElements.add(elem);
        }

        private void moveAllToRight() {
            rightTableElements.addAll(leftTableElements);
            leftTableElements.clear();
        }

        private void moveAllToLeft() {
            leftTableElements.addAll(rightTableElements);
            rightTableElements.clear();
        }

        private void addButtonListener() {
            shuttlePanel.addOnItemMoveButtonListener(new ShuttlePanel.OnItemMoveButtonListener<T>() {
                @Override
                public void itemMoveButtonPressed(int buttonId, List<T> selectedLeftTableElements, List<T> selectedRightTableElements) {

                    switch (buttonId) {
                        case ShuttlePanel.RIGHT_BUTTON:
                            moveElementsToRight(selectedLeftTableElements);
                            break;

                        case ShuttlePanel.LEFT_BUTTON_ID:
                            moveElementsToLeft(selectedRightTableElements);
                            break;

                        case ShuttlePanel.ALL_RIGHT_BUTTON_ID:
                            moveAllToRight();
                            break;

                        case ShuttlePanel.ALL_LEFT_BUTTON_ID:
                            moveAllToLeft();
                            break;
                    }
                    refreshView();
                }
            });

        }

        private void refreshView() {
            shuttlePanel.refreshData(leftTableElements, rightTableElements);
        }

        private void moveElementsToRight(List<T> elements) {
            leftTableElements.removeAll(elements);
            rightTableElements.addAll(elements);
        }

        private void moveElementsToLeft(List<T> elements) {
            rightTableElements.removeAll(elements);
            leftTableElements.addAll(elements);
        }

        //TODO javadoc
        public void addElements(Collection<T> elements) {
            leftTableElements.addAll(elements);
            refreshView();
        }

        //TODO javadoc
        public void addElement(T element) {
            leftTableElements.add(element);
            refreshView();
        }

        //TODO javadoc
        public void removeElement(T element) {
            leftTableElements.remove(element);
            refreshView();
        }

        //PAS
        public void init(ArrayList<T> leftElements, ArrayList<T> rightElements){
            leftInitialTableElements.clear();
            leftInitialTableElements.addAll(leftElements);
            leftTableElements.clear();
            leftTableElements.addAll(leftElements);
            rightInitialTableElements.clear();
            rightInitialTableElements.addAll(rightElements);
            rightTableElements.clear();
            rightTableElements.addAll(rightElements);
            refreshView();
        }

        public void reinit(){
            leftTableElements.clear();
            leftTableElements.addAll(leftInitialTableElements);
            rightTableElements.clear();
            rightTableElements.addAll(rightInitialTableElements);
            refreshView();
        }
        /*
            public ArrayList<T> getRightTableElements() {
                return rightTableElements;
            }
        */
        public void setResultat(boolean ok){
            if (ok){
                resultat = rightTableElements;
            } else {
                resultat =  rightInitialTableElements;
            }
        }

        public ArrayList<T> getResultat() {
            return resultat;
        }


    }
