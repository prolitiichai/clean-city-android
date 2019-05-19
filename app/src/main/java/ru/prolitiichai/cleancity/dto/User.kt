package ru.prolitiichai.cleancity.dto


class User(
    var avatarAddress: String,
    var trashCleaned: Int,
    var trashFound: Int,
    var karma: Int,
    var nickName: String
) {
}
