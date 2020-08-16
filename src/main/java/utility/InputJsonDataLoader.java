package utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class InputJsonDataLoader {

    private JSONObject jsonObject;

    // Read input json file and load data.
    public void loadJsonData() {
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader(Constants.INPUT_FILE_PATH));
            jsonObject = (JSONObject)((JSONObject) obj).get(Constants.MACHINE);

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Get the total no of outlets of machine.
    public int getOutletsCount() {

        JSONObject outlets = (JSONObject)jsonObject.get(Constants.OUTLETS);
        int outletsCount = ((Long)outlets.get(Constants.OUTLETS_COUNT)).intValue();
        return outletsCount;
    }

    // Get the ingredients total quantity to be refilled.
    public HashMap<String, Long> getTotalItemsQuantity() {
        return (JSONObject)jsonObject.get(Constants.TOTAL_ITEM_QUANTITY);
    }

    // Get all the beverages to be made
    public HashMap<String, Object> getBeverages() {
        return (JSONObject)jsonObject.get(Constants.BEVERAGES);
    }

}
