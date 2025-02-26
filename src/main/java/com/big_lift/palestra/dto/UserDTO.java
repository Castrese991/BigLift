package com.big_lift.palestra.dto;

import java.time.Instant;

import com.big_lift.palestra.enums.Role;
import com.big_lift.palestra.view.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

	@JsonView(Views.Public.class)
	@JsonIgnore
	private Long id;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class, Views.LoginView.class })
	private String username;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private Role role;
	@JsonView({ Views.CreateView.class, Views.UpdateView.class })
	private String email;
	@JsonView(Views.Public.class)
	private Instant createdAt;
	@JsonView({Views.CreateView.class, Views.LoginView.class})
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

}
