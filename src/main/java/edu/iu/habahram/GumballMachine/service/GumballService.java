package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.*;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GumballService implements IGumballService{

    IGumballRepository gumballRepository;

    public GumballService(IGumballRepository gumballRepository) {
        this.gumballRepository = gumballRepository;
    }

    private TransitionResult operate(String id, Transition operation) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = null;
        switch(operation) {
            case INSERT_QUARTER:
                result = machine.insertQuarter();
                break;
            case EJECT_QUARTER:
                result = machine.ejectQuarter();
                break;
            case TURN_CRANK:
                result = machine.turnCrank();
                break;
        }
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        return operate(id, Transition.INSERT_QUARTER);
    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {
        return operate(id, Transition.EJECT_QUARTER);
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {
        return operate(id, Transition.TURN_CRANK);
    }

    

    @Override
    public List<GumballMachineRecord> findAll() throws IOException {
        return gumballRepository.findAll();
    }

    @Override
    public GumballMachineRecord findById(String id) throws IOException {
        return gumballRepository.findById(id);
    }

    @Override
    public String save(GumballMachineRecord gumballMachineRecord) throws IOException {
        return gumballRepository.save(gumballMachineRecord);
    }
}
