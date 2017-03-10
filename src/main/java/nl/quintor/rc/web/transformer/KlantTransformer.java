package nl.quintor.rc.web.transformer;

import nl.quintor.rc.model.Geslacht;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.web.rest.dto.KlantDto;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

@Component
public class KlantTransformer implements nl.quintor.rc.web.transformer.Transformer<KlantDto, Klant> {
    public static final Transformer<Klant, KlantDto> TO_DTO_TRANSFORMER = new Transformer<Klant, KlantDto>() {

        @Override
        public KlantDto transform(Klant klant) {
            KlantDto klantDto = new KlantDto();
            klantDto.setKlantnummer(klant.getId());
            klantDto.setVoorletters(klant.getVoorletters());
            klantDto.setAchternaam(klant.getAchternaam());
            klantDto.setGeslacht(klant.getGeslacht() != null ? klant.getGeslacht().name() : null);
            if (klant.getAdres() != null) {
                klantDto.setStraat(klant.getAdres().getStraat());
                klantDto.setHuisnummer(klant.getAdres().getHuisnummer());
                klantDto.setHuisnummerToevoeging(klant.getAdres().getHuisnummerToevoeging());
                klantDto.setPostcode(klant.getAdres().getPostcode());
                klantDto.setWoonplaats(klant.getAdres().getWoonplaats());
            }
            klantDto.setBsn(klant.getBsn());
            klantDto.setTelefoonNummer(klant.getTelefoonNummer());
            klantDto.setEmail(klant.getEmail());

            return klantDto;
        }
    };

    public static final Transformer<KlantDto, Klant> TO_MODEL_TRANSFORMER = new Transformer<KlantDto, Klant>() {

        @Override
        public Klant transform(KlantDto klantDto) {
            Klant klant = new Klant();
            klant.setId(klantDto.getKlantnummer());
            klant.setVoorletters(klantDto.getVoorletters());
            klant.setAchternaam(klantDto.getAchternaam());
            klant.setGeslacht(klantDto.getGeslacht() != null ? Geslacht.valueOf(klantDto.getGeslacht()) : null);
            klant.getAdres().setStraat(klantDto.getStraat());
            klant.getAdres().setHuisnummer(klantDto.getHuisnummer());
            klant.getAdres().setHuisnummerToevoeging(klantDto.getHuisnummerToevoeging());
            klant.getAdres().setPostcode(klantDto.getPostcode());
            klant.getAdres().setWoonplaats(klantDto.getWoonplaats());
            klant.setBsn(klantDto.getBsn());
            klant.setTelefoonNummer(klantDto.getTelefoonNummer());
            klant.setEmail(klantDto.getEmail());

            return klant;
        }
    };

    @Override
    public KlantDto toDto(Klant klant) {
        return TO_DTO_TRANSFORMER.transform(klant);
    }

    @Override
    public Klant toModel(KlantDto klantDto) {
        return TO_MODEL_TRANSFORMER.transform(klantDto);
    }
}

