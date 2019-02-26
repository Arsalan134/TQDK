import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
	@Override
	public int compare(Student s1, Student s2) {
		return s2.getScore() - s1.getScore();
	}
}