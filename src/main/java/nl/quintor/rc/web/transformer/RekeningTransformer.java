package nl.quintor.rc.web.transformer;

import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.repository.KlantRepository;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class RekeningTransformer implements nl.quintor.rc.web.transformer.Transformer <RekeningDto, Rekening> {
    @Autowired
    private KlantTransformer klantTransformer;

    @Autowired
    private KlantRepository klantRepository;
    public final Transformer<Rekening, RekeningDto> TO_DTO_TRANSFORMER = new Transformer<Rekening, RekeningDto>() {
        private final FastDateFormat sdf = FastDateFormat.getInstance("dd-MM-yyyy HH:mm:ss");
        @Override
        public RekeningDto transform(Rekening rekening) {
            RekeningDto rekeningDto = new RekeningDto();
            rekeningDto.setRekeningNummer(rekening.getRekeningNummer());
            rekeningDto.setSaldo(rekening.getSaldo());
            rekeningDto.setType(rekening.getType());
            rekeningDto.setStatus(rekening.getStatus() != null ? rekening.getStatus().name() : null);
            rekeningDto.setBeginDatum(sdf.format(rekening.getBeginDatum()));
            return rekeningDto;
        }
    };
    public Transformer<RekeningDto, Rekening> TO_MODEL_TRANSFORMER = new Transformer<RekeningDto, Rekening>() {
        @Override
        public Rekening transform(RekeningDto rekeningDto) {
            Rekening rekening = new Rekening();
            rekening.setBeginDatum(new Date());
            rekening.setRekeningNummer(rekening.getRekeningNummer());
            rekening.setType(rekeningDto.getType());

            Collection<Klant> klanten = CollectionUtils.collect(rekeningDto.getKlanten(), KlantTransformer.TO_MODEL_TRANSFORMER);
            for (Klant klant: klanten) {
                //rekening.addRekeninghouder(klantRepository.getKlantByKlantNummer(klant.getId()));
            }
            return rekening;
        }
    };

    @Override
    public RekeningDto toDto(Rekening rekening) {
        return TO_DTO_TRANSFORMER.transform(rekening);
    }

    @Override
    public Rekening toModel(RekeningDto rekeningDto) {
        return TO_MODEL_TRANSFORMER.transform(rekeningDto);
    }
}
