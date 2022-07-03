package com.ssd.springframework.services;

import com.ssd.springframework.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    public Set<UnitOfMeasureCommand> listAllUoms();
}
