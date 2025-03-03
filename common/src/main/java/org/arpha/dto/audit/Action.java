package org.arpha.dto.audit;

public enum Action {
    CREATE_USER, FIND_USER_BY_ID, FIND_USER_BY_EMAIL, UPDATE_USER, DELETE_USER_BY_ID, ACTIVATE_ACCOUNT, UPLOAD_FILE,
    DELETE_FILE_BY_ID, FIND_FILE_BY_ID, CREATE_CATEGORY, DELETE_CATEGORY_BY_ID, CREATE_GENRE, DELETE_GENRE_BY_ID,
    CREATE_PRODUCT, DELETE_PRODUCT_BY_ID, ADD_GENRE_TO_PRODUCT, ADD_CATEGORY_TO_PRODUCT;
}
