import distribution.Distribution;
import distribution.DistributionList;
import javafx.scene.control.TabPane;
import ui.*;

import java.util.ArrayList;
import java.util.function.Function;

public class SessionController {

    private GraphTab tabFrame;
    private ArrayList<TabContent> tabContents;
    private DistributionList distributions;
    private TabContentList tabContentList;
    private TabPane tabPane;

    public SessionController(TabPane tabPane, String title) {
        tabFrame = new GraphTab(title);
        tabContents = buildTabUi();
        tabContentList = new TabContentList(tabContents);
        tabContents.forEach(tab -> tab.setComboBox(tabContentList));
        tabFrame.setContent(tabContents.get(0));
        tabPane.getTabs().add(tabFrame);
    }


    private ArrayList<TabContent> buildTabUi() {
        var tabContents = new ArrayList<TabContent>();
        tabContents.add(new UiBinomial(new Callback()));
        tabContents.add(new UiHypergeometric(new Callback()));
        return tabContents;
    }

    public DistributionList getDistrubitions() {
        return distributions;
    }


    class Callback implements Function<String, Distribution> {
        @Override
        public Distribution apply(String newDistribution) {
            for (var content: tabContents) {
                if (content.toString().equals(newDistribution)) {
                    tabFrame.setContent(content);
                    content.comboBoxSelect();
                }
            }
            return null;
        }

    }

}
