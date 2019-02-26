import java.util.ArrayList;
import java.util.Random;

public class Student {
	private int id;
	private String name;
	private int score;
	private ArrayList<Preference> preferenceList;
	private Random r;
	// private int maxPreferenceListLenth = 3;
	// private int minPreferenceListLenth = 1;
	private University acceptedUniversity;

	public Student(int score, int id) {
		this.setName("Arsalan " + (id - 1000 + 1));
		this.id = id;
		this.score = score;
		preferenceList = new ArrayList<>();
		fillPreferenceList();
	}

	int acceptedToUniversity() {
		for (int i = 0; i < preferenceList.size(); i++)
			if (preferenceList.get(i).getState() == State.tentativelyAccepted)
				return i;
		return preferenceList.size();
	}

	double happinessLevel() {
		for (int i = 0; i < preferenceList.size(); i++)
			if (preferenceList.get(i).getState() == State.tentativelyAccepted)
				return (preferenceList.size() - i) * 1.0 / preferenceList.size() * 100;
		return 0.0;
	}

	boolean hasBeenAcceptedAnywhere() {
		for (Preference preference : preferenceList)
			if (preference.getState() == State.tentativelyAccepted)
				return true;
		return false;
	}

	private void fillPreferenceList() {
		r = new Random();

		// int preferenceListLength =
		// Math.min(r.nextInt(Main.universities.size() - minPreferenceListLenth)
		// + minPreferenceListLenth, maxPreferenceListLenth);
		int preferenceListLength = Main.universities.size();
		for (int prefrence = 0; prefrence < preferenceListLength; prefrence++) {
			addPreference(getRandomUniversityThatIsNotInPreference());
		}
	}

	University getNotRejectedUniversity() {
		for (Preference preference : preferenceList) {
			if (preference.getState() == State.none)
				return preference.getUniversity();
		}
		return null;
	}

	private University getRandomUniversityThatIsNotInPreference() {
		int indexOfUni = r.nextInt(Main.universities.size());
		if (universityIsInsidePrefernece(Main.universities.get(indexOfUni))) {
			return getRandomUniversityThatIsNotInPreference();
		}
		return Main.universities.get(indexOfUni);
	}

	private boolean universityIsInsidePrefernece(University university) {
		for (int i = 0; i < preferenceList.size(); i++)
			if (university.getName().equals(preferenceList.get(i).getUniversity().getName()))
				return true;
		return false;
	}

	public int getScore() {
		return score;
	}

	public ArrayList<Preference> getPreferenceList() {
		return preferenceList;
	}

	public void addPreference(University university) {
		preferenceList.add(new Preference(university, State.none));
	}

	public ArrayList<Preference> printReferenceList() {
		return preferenceList;
	}

	@Override
	public String toString() {
		return name + " \tScore: " + score + " \tPreference: " + preferenceList + "\n";
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public University getAcceptedUniversity() {
		return acceptedUniversity;
	}

	public void setAcceptedUniversity(University acceptedUniversity) {
		this.acceptedUniversity = acceptedUniversity;
	}

}
