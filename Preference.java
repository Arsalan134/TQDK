
public class Preference {

	private University university;
	private State state;

	public Preference(University university, State state) {
		this.university = university;
		this.state = state;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Override
	public String toString() {
		switch (state) {
		case tentativelyAccepted:
			return university.getName() + ": ✅";
		case rejected:
			return university.getName() + ": ❌";
		case none:
			return university.getName() + ": ❔";
		default:
			return university.getName() + ": " + state;
		}

	}

}
