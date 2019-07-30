package com.soho.watcher.aws.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soho.watcher.aws.mapper.InstanceStateView;
import com.soho.watcher.aws.mapper.InstanceStateViewMixIn;
import com.soho.watcher.aws.mapper.LoadBalancerAttributesView;
import com.soho.watcher.aws.mapper.LoadBalancerAttributesViewMixIn;


public class JsonHelper {
	/*private JsonHelper() {}

	private static final ObjectMapper mapper;
	private static final MappingJsonFactory factory;

	static {
		mapper = AmazonObjectMapperConfigurer.createConfigured()
				.addMixIn(InstanceStateView.class, InstanceStateViewMixIn.class)
				.addMixIn(LoadBalancerAttributesView.class, LoadBalancerAttributesViewMixIn.class);
		
		factory = new MappingJsonFactory(mapper);
	}

	public static JsonParser createParser(InputStream input) throws IOException {
		return factory.createParser(input);
	}

	public static JsonParser createParser(Reader input) throws IOException {
		return factory.createParser(input);
	}

	public static <T> T decode(Class<T> c, InputStream input) throws IOException {
		try {
			TypeReference<T> ref = new TypeReference<T>() {};
			return createParser(input).readValueAs(ref);
		}
		finally {
			input.close();
		}
	}

	public static <T> T decode(Class<T> c, Reader input) throws IOException {
		try {
			TypeReference<T> ref = new TypeReference<T>() {};
			return createParser(input).readValueAs(ref);
		}
		finally {
			input.close();
		}
	}

	public static <T> T decode(Class<T> c, String json) throws IOException {
		return decode(c, new StringReader(json));
	}*/
}
