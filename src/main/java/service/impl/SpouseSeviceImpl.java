package service.impl;

import entity.Spouse;
import repository.SpouseRepository;
import service.SpouseService;

public class SpouseSeviceImpl implements SpouseService {
    private final SpouseRepository spouseRepository;

    public SpouseSeviceImpl(SpouseRepository spouseRepository) {
        this.spouseRepository = spouseRepository;
    }

    @Override
    public Spouse save(Spouse spouse) {
       return spouseRepository.save(spouse);

    }
}
