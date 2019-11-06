import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test extends JSONArray{

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public static void main(String[] args) {

    	List<JSONObject> highscores = new ArrayList<JSONObject>();
    	Random rand = new Random();
    	for (int i = 0; i < 10; i++) {
    		JSONObject obj = new JSONObject();
    		obj.put("Name", "Victor");
    		obj.put("Score", rand.nextInt(20));
    		highscores.add(obj);
    	}
    	
    	Collections.sort(highscores, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject arg0, JSONObject arg1) {
				int val1, val2;
				
				val1 = (int) arg0.get("Score");
				val2 = (int) arg1.get("Score");
				
				int result;
				
				if (val1 < val2) {
					result = -1;
				} else if (val1 == val2) {
					result = 0;
				} else {
					result = 1;
				}
				return result;
			}
    	});
    	
    	System.out.println(highscores.toString());
    	
    }
    
    
    
}