package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.exception.UserExceptions;
import cvut.fel.cz.thesis_helper.model.Requirement;
import cvut.fel.cz.thesis_helper.repository.RequirementRepository;
import cvut.fel.cz.thesis_helper.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;

    @Autowired
    public RequirementServiceImpl(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @Override
    public void removeRequirement(Integer id) {
        Optional<Requirement> requirementOpt = requirementRepository.findById(id);
        if(requirementOpt.isEmpty())
            throw UserExceptions.accessDeniedException(); //TODO
        requirementRepository.delete(requirementOpt.get());
    }
}
