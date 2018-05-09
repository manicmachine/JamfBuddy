package net.manicmachine.csather.dao;

import net.manicmachine.csather.model.Computer;
import java.util.ArrayList;

public interface ComputerDao {

    Computer getComputer(int computerId);

    ArrayList<Computer> getAllComputers();

    void deleteComputer(int computerId);
    void addComputer(Computer computer);
}