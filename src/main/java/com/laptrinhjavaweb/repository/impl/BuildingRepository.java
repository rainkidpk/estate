package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.repository.IBuildingRepository;

public class BuildingRepository extends AbstractJDBC<BuildingEntity> implements IBuildingRepository {

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder, Pageble pageble) {
		Map<String, Object> properties = buildMapSearch(buildingSearchBuilder);
		StringBuilder whereClause = new StringBuilder();
		if (StringUtils.isNotBlank(buildingSearchBuilder.getCostRentFrom())) {
			whereClause.append(" AND costrent >= " + buildingSearchBuilder.getCostRentFrom() + "");
		}
		if (StringUtils.isNotBlank(buildingSearchBuilder.getCostRentTo())) {
			whereClause.append(" AND costrent <= " + buildingSearchBuilder.getCostRentTo() + "");
		}

		if (StringUtils.isNotBlank(buildingSearchBuilder.getAreaRentFrom())
				|| StringUtils.isNotBlank(buildingSearchBuilder.getAreaRentTo())) {
			whereClause.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (ra.buildingid = A.id");
			if (buildingSearchBuilder.getAreaRentFrom() != null) {
				whereClause.append(" AND ra.value >= '" + buildingSearchBuilder.getAreaRentFrom() + "'");
			}
			if (buildingSearchBuilder.getAreaRentTo() != null) {
				whereClause.append(" AND ra.value <= '" + buildingSearchBuilder.getAreaRentTo() + "'");
			}
			whereClause.append("))");
		}
		if (buildingSearchBuilder.getBuildingTypes().length > 0) {
			whereClause.append(" AND (A.type LIKE '%" + buildingSearchBuilder.getBuildingTypes()[0] + "%'");
			/*
			 * for (String type : buildingSearchBuilder.getBuildingTypes()) { if
			 * (!type.equals(buildingSearchBuilder.getBuildingTypes())) {
			 * whereClause.append(" OR A.type LIKE '%" +
			 * buildingSearchBuilder.getBuildingTypes() + "%'"); } }
			 */
			Arrays.stream(buildingSearchBuilder.getBuildingTypes())
					.filter(item -> !item.equals(buildingSearchBuilder.getBuildingTypes()[0]))
					.forEach(item -> whereClause.append(" OR A.type LIKE '%" + item + "%'"));

			whereClause.append(")");
		}
		return findAll(properties, pageble, whereClause.toString());
	}

	private Map<String, Object> buildMapSearch(BuildingSearchBuilder buildingSearchBuilder) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field field : fields) {
				if (!field.getName().equals("buildingTypes") && !field.getName().startsWith("costRent")
						&& !field.getName().startsWith("areaRent")) {
					field.setAccessible(true);
					if (field.get(buildingSearchBuilder) != null) {
						if (field.getName().equals("buildingArea") || field.getName().equals("numberOfBasement")) {
							if (field.get(buildingSearchBuilder) != "") {
								result.put(field.getName().toLowerCase(),
										Integer.parseInt(((String) field.get(buildingSearchBuilder))));
							}

						} else {
							result.put(field.getName().toLowerCase(), field.get(buildingSearchBuilder));
						}

					}

				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}

}
