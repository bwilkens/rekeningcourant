package nl.quintor.rc.web.rest.dto;

public enum GebruikerRolType {
    KLANT("klant"), MEDEWERKER("admin");
    private String rol;
    GebruikerRolType(String rol) {
        this.rol = rol;
    }
    public String getRol() {
        return this.rol;
    }
}
