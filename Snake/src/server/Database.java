package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

class Database {

	private File file;
	private FileWriter writer;
	private FileInputStream reader;

	Database() {
		file = new File("highscores");

		if (!file.exists()) {
			try {
				writer = new FileWriter(file);
				writer.write("[]");
				writer.close();
			} catch (Exception e) {
			}
		}

		// updateHighscore("Victor", 15);

	}

	public void updateHighscore(String name, int score) {

		try {
			reader = new FileInputStream(file);
			JSONArray arr = new JSONArray(new String(reader.readAllBytes(), "UTF8"));

			List<JSONObject> highscores = new ArrayList<JSONObject>();
			for (Object obj : arr) {
				highscores.add((JSONObject) obj);
			}

			if (scoreFitsHighscores(highscores, score)) {

				int index = getPosition(highscores, score);

				if (highscores.size() == 20) {
					// Removes the last highscore if list is full
					highscores.remove(0);
				}

				JSONObject user = new JSONObject();
				user.put("Name", name);
				user.put("Score", score);
				user.put("Rank", index + 1);
				highscores.add(index, user);
			}

			highscores.sort(new Comparator<JSONObject>() {

				@Override
				public int compare(JSONObject user1, JSONObject user2) {

					int score1 = (user1).getInt("Score");
					int score2 = (user2).getInt("Score");

					if (score1 < score2) {
						return 1;
					} else if (score1 > score2) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			updateRanks(highscores);
			save(highscores);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateRanks(List<JSONObject> highscores) {
		for (int i = 0; i < highscores.size(); i++) {
			highscores.get(i).put("Rank", i + 1);
		}
	}

	private void save(List<JSONObject> highscores) {
		try {
			writer = new FileWriter(file);
			writer.write(highscores.toString());
			writer.close();
		} catch (Exception e) {
		}
	}

	private int getPosition(List<JSONObject> highscores, int score) {

		// Returns the position that the score should be inserted to.
		// If it can't be found, the last index is return (this is
		// only true if the total size of the highscore is less than 20

		for (int i = 0; i < highscores.size(); i++) {
			if (highscores.get(i).getInt("Score") < score) {
				return i;
			}
		}
		return highscores.size();
	}

	private boolean scoreFitsHighscores(List<JSONObject> highscores, int score) {

		if (highscores.size() < 20) {
			return true;
		}

		for (Object user : highscores) {
			if (((JSONObject) user).getInt("Score") < score) {
				return true;
			}
		}
		return false;
	}

	public String getHighscores() {
		try {
			reader = new FileInputStream(file);
			return (new String(reader.readAllBytes(), "UTF8"));
		} catch (IOException e) {
			return "";
		}
	}

}
