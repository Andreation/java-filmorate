package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.DAO.MpaDbStorage;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public Collection<Mpa> getAllMpa() {
        return mpaDbStorage.getAll();
    }

    public Mpa getMpa(Integer id) {
        try {
            return mpaDbStorage.getMpa(id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("MPA rating not found");
        }

    }


}
