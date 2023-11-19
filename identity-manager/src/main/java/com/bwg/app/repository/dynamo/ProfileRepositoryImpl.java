package com.bwg.app.repository.dynamo;

import com.bwg.app.model.profile.Profile;
import com.bwg.app.model.profile.ProfileType;
import com.bwg.app.repository.ProfileRepository;
import com.bwg.app.repository.dynamo.entity.EntityMapper;
import com.bwg.app.repository.dynamo.entity.ProfileEntity;
import com.bwg.app.repository.dynamo.repository.ProfileDynamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileDynamoRepository repository;

    @Override
    public Profile insert(String username, Profile profile) {
        return repository.insert(EntityMapper.toProfileEntity(username, profile)).getProfile();
    }

    @Override
    public Profile update(String username, Profile profile) {
        return repository.update(EntityMapper.toProfileEntity(username, profile)).getProfile();
    }

    @Override
    public Optional<Profile> findById(String username, ProfileType type, String userkey) {
        return repository.findById(username, type.toString(), userkey)
                .map(ProfileEntity::getProfile);
    }

    @Override
    public List<Profile> findByProfileType(String username, ProfileType type) {
        return repository.findByProfileType(username, type.toString()).stream()
                .map(ProfileEntity::getProfile)
                .toList();
    }
}
