package penta.database.core;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import penta.database.core.enums.PKType;
import penta.database.core.enums.Type;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public
@interface EntityType
{
	Type type();
	PKType pkType();
}
