package co.piui.api.config;

	import com.typesafe.config.Config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors( fluent = true )
@RequiredArgsConstructor
public class HibernateConfig {

	private final Config config;

	@Getter( lazy = true )
	private final String entitiManager = config().getString( "piui.hibernate.entity" );
}
