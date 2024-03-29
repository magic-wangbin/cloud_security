//package com.magic.security.service.resource;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//public class User implements UserDetails {
//
//    private Long id;
//    private String username;
//    private String password;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    /**
//     * Returns the authorities granted to the user. Cannot return <code>null</code>.
//     *
//     * @return the authorities, sorted by natural key (never <code>null</code>)
//     */
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
//    }
//
//    /**
//     * Returns the password used to authenticate the user.
//     *
//     * @return the password
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
//     *
//     * @return the username (never <code>null</code>)
//     */
//    public String getUsername() {
//        return username;
//    }
//
//    /**
//     * Indicates whether the user's account has expired. An expired account cannot be
//     * authenticated.
//     *
//     * @return <code>true</code> if the user's account is valid (ie non-expired),
//     * <code>false</code> if no longer valid (ie expired)
//     */
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is locked or unlocked. A locked user cannot be
//     * authenticated.
//     *
//     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
//     */
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user's credentials (password) has expired. Expired
//     * credentials prevent authentication.
//     *
//     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
//     * <code>false</code> if no longer valid (ie expired)
//     */
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    /**
//     * Indicates whether the user is enabled or disabled. A disabled user cannot be
//     * authenticated.
//     *
//     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
//     */
//    public boolean isEnabled() {
//        return true;
//    }
//}
