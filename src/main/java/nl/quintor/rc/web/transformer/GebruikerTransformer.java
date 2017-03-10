package nl.quintor.rc.web.transformer;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Medewerker;
import nl.quintor.rc.web.rest.dto.*;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;

import java.text.DateFormat;

@Component
public class GebruikerTransformer implements nl.quintor.rc.web.transformer.Transformer<GebruikerDto, Gebruiker> {
    private static final Transformer<Gebruiker, GebruikerDto> TO_DTO_TRANSFORMER = new Transformer<Gebruiker, GebruikerDto>() {
        @Override
        public GebruikerDto transform(Gebruiker gebruiker) {
            GebruikerDto gebruikerDto;
            if (gebruiker instanceof Klant) {
                gebruikerDto = new KlantDto();
                KlantDto klantDto = (KlantDto) gebruikerDto;
                Klant klant = (Klant) gebruiker;
                klantDto.setRol(GebruikerRolType.KLANT.getRol());
                klantDto.setKlantnummer(klant.getId());
                klantDto.setDisplayName(String.format("%s %s", klant.getVoorletters(), klant.getAchternaam()));
            } else if (gebruiker instanceof Medewerker) {
                gebruikerDto = new MedewerkerDto();
                MedewerkerDto medewerkerDtoDto = (MedewerkerDto) gebruikerDto;
                medewerkerDtoDto.setRol(GebruikerRolType.MEDEWERKER.getRol());
                Medewerker medewerker = (Medewerker) gebruiker;
                medewerkerDtoDto.setDisplayName(String.format("%s (%s)", gebruiker.getLoginnaam(), medewerker.getMedewerkerNummer()));
                medewerkerDtoDto.setMedewerkerId(gebruiker.getId());
            } else {
                throw new IllegalStateException("onbekend subtype van gebruiker");
            }
            gebruikerDto.setUsername(gebruiker.getLoginnaam());
            if (gebruiker.getVoorlaatstIngelogd() != null) {
                gebruikerDto.setLaatstIngelogd(DateFormat.getDateTimeInstance(
                        DateFormat.MEDIUM, DateFormat.SHORT).format(gebruiker.getVoorlaatstIngelogd()));
            }
            return gebruikerDto;
        }
    };
    private static Transformer<GebruikerDto, Gebruiker> TO_MODEL_TRANSFORMER = new Transformer<GebruikerDto, Gebruiker>() {
        @Override
        public Gebruiker transform(GebruikerDto gebruikerDto) {
            throw new NotImplementedException();
        }
    };

    @Override
    public GebruikerDto toDto(Gebruiker gebruiker) {
        return TO_DTO_TRANSFORMER.transform(gebruiker);
    }

    @Override
    public Gebruiker toModel(GebruikerDto gebruikerDto) {
        return TO_MODEL_TRANSFORMER.transform(gebruikerDto);
    }
}
