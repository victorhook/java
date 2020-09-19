package ui;

import javafx.collections.ObservableListBase;
import javafx.scene.control.Tab;

import java.util.ArrayList;

public class TabContentList extends ObservableListBase {

    private ArrayList<String> tabContents;

    public TabContentList(ArrayList<TabContent> tabContents) {
        this.tabContents = new ArrayList<>();
        for (TabContent tab: tabContents) {
            this.tabContents.add(tab.toString());
        }
    }

    @Override
    public Object get(int i) {
        return tabContents.get(i);
    }

    @Override
    public int size() {
        return tabContents.size();
    }
}
