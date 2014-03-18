package in.kmbs.vlethyme.controllers;

import java.util.List;

public interface ListVOConverter<T> {
	public List<T> convertTOVO(List entityList);
}
