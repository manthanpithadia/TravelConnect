fun List<LocationItem>.toLocationEntities(): List<LocationEntity> {
    return this.map { locationItem ->
        LocationEntity(
            id = locationItem.id,
            name = locationItem.name,
            province = locationItem.province,
            imageUrl = locationItem.img,
            rating = locationItem.rating
        )
    }
}