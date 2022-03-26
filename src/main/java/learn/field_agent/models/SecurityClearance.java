package learn.field_agent.models;

import java.util.Objects;

public class SecurityClearance {

    private static int securityClearanceId;  //the JdbcTempRepo wanted these both to be static (b/c of the add method)
    private static String name;

    public SecurityClearance() {
    }

    public SecurityClearance(int securityClearanceId, String name) {
        SecurityClearance.securityClearanceId = securityClearanceId;
        SecurityClearance.name = name;
    }

    public static int getSecurityClearanceId() {
        return securityClearanceId;
    }

    public void setSecurityClearanceId(int securityClearanceId) {
        SecurityClearance.securityClearanceId = securityClearanceId;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        SecurityClearance.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityClearance that = (SecurityClearance) o;
        return securityClearanceId == securityClearanceId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityClearanceId, name);
    }


}
