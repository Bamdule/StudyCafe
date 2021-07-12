package com.bamdule.studycafe.entity.serverconfig;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerConfigRepository extends JpaRepository<ServerConfig, Integer> {

    public ServerConfig findServerConfigByName(String name);
}
