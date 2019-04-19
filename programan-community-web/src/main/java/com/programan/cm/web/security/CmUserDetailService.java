package com.programan.cm.web.security;

import com.programan.cm.db.dao.RoleDao;
import com.programan.cm.db.dao.UserRoleDao;
import com.programan.cm.db.model.User;
import com.programan.cm.db.model.UserRole;
import com.programan.cm.repository.db.UserRepository;
import com.programan.cm.web.manager.UserManager;
//import com.programan.cm.repository.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Slf4j
public class CmUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userManager.selectByUserName(userName);
        if (user==null){
//            throw new AuthenticationCredentialsNotFoundException("authError");
            return null;
        }
        log.info("{}",user);
        List<UserRole> userRole = userRoleDao.selectByUserId(user);
        log.info("{}",userRole);
        List<GrantedAuthority> authorities = new ArrayList<>();
        userRole.forEach(userRole1 -> authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_"+roleDao.selectNameById(userRole1.getRole().getId()))));
        log.info("{}",authorities);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPwd(),authorities);
    }

}
