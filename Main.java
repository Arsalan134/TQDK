import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	static String namesUni[] = { "Academy of Public Administration", "Azerbaijan Architecture and Construction University", "ADA University",
			"Azerbaijan Medical University", "Azerbaijan State Agricultural Universiy", "Azerbaijan State Economic University", "Azerbaijan State Marine Academy",
			"Azerbaijan State Oil and Industry University", "Azerbaijan State University of Culture and Arts",
			"Academy of State Customs Committee of the Republic of Azerbaijan", "Azerbaijan State Academy Of Physical Education And Sport",
			"Azerbaijan University of Architecture and Construction", "Azerbaijan Tourism and Management University", "National Conservatory of Azerbaijan",
			"Azerbaijan State Pedagogical University", "Azerbaijan Technical University", "Azerbaijan University of Languages", "Baku Engineering Universiy",
			"Baku Academy of Music", "Baku Higher Oil School", "National Aviation Academy", "Baku Slavic University", "Baku State University",
			"Theology Institute of Azerbaijan", "Ganja State University", "Lankaran State University", "Mingachevir State University", "Nakhchivan State University",
			"Sumqayit State University" };

	static ArrayList<Student> students = new ArrayList<>();
	static ArrayList<University> universities = new ArrayList<>();

	static int numberOfStudents = 10;
	static int numberOfUniversities = Math.min(namesUni.length, 4);
	static int maxScore = 700;
	static double delta = 0.9;

	public static void main(String[] args) {

		Random r = new Random();

		for (int i = 0; i < numberOfUniversities; i++) {
			University newUniversity = new University();
			newUniversity.setName(namesUni[i]);
			newUniversity.setName((char) (i + 65) + "");
			universities.add(newUniversity);
		}

		for (int i = 0; i < numberOfStudents; i++) {
			int randomScore = r.nextInt(maxScore + 1);
			Student newStudent = new Student(randomScore, i + 1000);
			students.add(newStudent);
		}

		// Collections.sort(students, new StudentComparator());

		System.out.flush();
		System.out.println("Number of Universities:\t" + universities.size());
		System.out.println("Number of Students:\t" + students.size());
		System.out.println("Number of Seats:\t\t" + totalNumberOfSeats());
		System.out.println();
		System.out.flush();

		System.out.println("List of Students:");
		System.out.println(students);

		startAlgo();

		System.out.flush();
		System.out.println("\n\n\n");
		System.out.println("List of Universities:");

		System.out.println(universities);

		System.out.println("\n\n\n");

		System.out.println("List of Students:");

		System.out.println(students);

		printNotRejectedStudents();

		System.out.println("\n\n\n");

		printHappiness();
	}

	static void printHappiness() {
		System.out.println("Name \t\tScore\t\t" + "AcceptedTo\t" + "Happiness %" + "\tPreference");
		NumberFormat formatter = new DecimalFormat("#0.000");
		for (Student student : students)
			System.out.println(student.getName() + "\t" + student.getScore() + "\t\t" + student.acceptedToUniversity() + "/" + student.getPreferenceList().size() + "\t\t"
					+ student.happinessLevel() + "\t\t" + student.getPreferenceList());

		double average = 0;
		for (Student student : students)
			average += student.happinessLevel();
		average /= 1.0 * students.size();
		System.out.println();
		System.out.println("Average Happiness:\t" + average + " %");
	}

	static void printNotRejectedStudents() {
		System.out.print("\n❌ Rejected Students:\n");
		for (Student student : students)
			if (!studentIsAccepted(student))
				System.out.print(student);
	}

	private static boolean studentIsAccepted(Student student) {
		boolean accepted = false;
		for (Preference p : student.getPreferenceList()) {
			if (p.getState() == State.tentativelyAccepted)
				accepted = true;
		}
		return accepted;
	}

	static int totalNumberOfSeats() {
		int temp = 0;
		for (University university : universities) {
			temp += university.availableSeats();
		}
		return temp;
	}

	private static void startAlgo() {
		/* This procedure continues until EITHER all students are admitted by a
		 * university, or unassigned students have no university to propose that
		 * has not rejected them. Azer Abizade© */

		while (!areAllStudentsAdmitted() && existUniversityThatHasNotBeenAppliedBySomeRejectedStudent())
			for (Student student : students) {
				if (student.hasBeenAcceptedAnywhere())
					continue;
				University u = student.getNotRejectedUniversity();
				if (u != null)
					u.processStudent(student);
			}
	}

	private static boolean existUniversityThatHasNotBeenAppliedBySomeRejectedStudent() {
		for (Student student : students) {
			if (student.hasBeenAcceptedAnywhere())
				continue;
			for (Preference preference : student.getPreferenceList())
				if (preference.getState() == State.none)
					return true;
		}
		return false;
	}

	private static boolean areAllStudentsAdmitted() {
		for (Student student : students)
			if (!student.hasBeenAcceptedAnywhere())
				return false;
		return true;
	}

}
