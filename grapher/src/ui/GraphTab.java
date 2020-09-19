package ui;

import javafx.scene.control.Tab;

public class GraphTab extends Tab {

    public GraphTab(String title) {
        super(title);
    }

    public GraphTab(String title, TabContent content) {
        super(title);
        this.setContent(content.getContent());
    }

    public void displayTab(TabContent content) {
        this.setContent(content.getContent());
    }

}
