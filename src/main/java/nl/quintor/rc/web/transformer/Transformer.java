package nl.quintor.rc.web.transformer;

/**
 * Created by marcel on 18-8-2014.
 */
public interface Transformer <DTO, MODEL> {
    DTO toDto(MODEL model);
    MODEL toModel(DTO dto);
}
