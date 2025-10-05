
import java.util.*;

public class MemberManager {
    private List<Member> members = new ArrayList<>();

    public String addMember(String rollNumber, String name) {
        for (Member m : members) {
            if (m.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return "❌ Member with roll number " + rollNumber + " already exists!\n";
            }
        }
        Member newMember = new Member(rollNumber, name);
        members.add(newMember);
        return "✅ Member added:\n" + newMember.toDisplayString() + "\n";
    }

    public String displayAll() {
        StringBuilder sb = new StringBuilder("👥 All Members:\n");
        if (members.isEmpty()) {
            sb.append("No members registered.\n");
        }
        for (Member m : members) {
            sb.append(m.toDisplayString()).append("\n");
        }
        return sb.toString();
    }

    public String searchByRollNumber(String rollNumber) {
        for (Member m : members) {
            if (m.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return "🔍 Found:\n" + m.toDisplayString() + "\n";
            }
        }
        return "❌ No member found with roll number: " + rollNumber + "\n";
    }

    public String updateMember(String rollNumber, String newName) {
        for (Member m : members) {
            if (m.getRollNumber().equalsIgnoreCase(rollNumber)) {
                m.setName(newName);
                return "✏️ Member updated:\n" + m.toDisplayString() + "\n";
            }
        }
        return "❌ Member with roll number " + rollNumber + " not found.\n";
    }

    public String deleteMember(String rollNumber) {
        Iterator<Member> iterator = members.iterator();
        while (iterator.hasNext()) {
            Member m = iterator.next();
            if (m.getRollNumber().equalsIgnoreCase(rollNumber)) {
                iterator.remove();
                return "🗑️ Member deleted:\n" + m.toDisplayString() + "\n";
            }
        }
        return "❌ Member with roll number " + rollNumber + " not found.\n";
    }

    public boolean isMemberExists(String rollNumber) {
        for (Member m : members) {
            if (m.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return true;
            }
        }
        return false;
    }
public List<Member> getMembers() {
        return this.members;
    }
    
    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
