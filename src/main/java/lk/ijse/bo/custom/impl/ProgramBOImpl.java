package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;

import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Program;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Programs);

    public boolean saveProgram(ProgramDTO programDTO) {
        return programDAO.save( new Program(programDTO.getpId(),programDTO.getpName(),programDTO.getDuration(),programDTO.getFee()));
    }

    public String getCurrentProgramId() throws SQLException {
        return programDAO.getCurrentId();
    }

    public boolean updateProgram(ProgramDTO programDTO) {
        return programDAO.update(new Program(programDTO.getpId(),programDTO.getpName(),programDTO.getDuration(),programDTO.getFee()));
    }


    public ProgramDTO searchProgramId(String id) {
        Program program = programDAO.searchId(id);
        return new ProgramDTO(program.getpId(),program.getpName(),program.getDuration(),program.getFee());
    }


    public List<ProgramDTO> getAllPrograms() throws SQLException,ClassNotFoundException{

        List<ProgramDTO> ProgramDTOS = new ArrayList<>();
        List<Program> Programs = programDAO.getAll();

        for (Program program:Programs) {
            ProgramDTO programDTO = new ProgramDTO(program.getpId(),program.getpName(),program.getDuration(),program.getFee());
            ProgramDTOS.add(programDTO);
        }
        return ProgramDTOS;
    }

    public boolean delete(String id){
        return programDAO.delete(id);
    }

    @Override
    public ProgramDTO searchProgramByName(String name) {
        Program program = programDAO.searchByName(name);
       return new ProgramDTO(program.getpId(),program.getpName(),program.getDuration(),program.getFee());
    }

    public List<String> getProgramNamesByStudentId(String studentId){
        return programDAO.getProgramNamesByStudentId(studentId);
    }
}
