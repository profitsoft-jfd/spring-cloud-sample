package com.springcloud.estates.persistence;

import com.springcloud.estates.model.Estate;

public interface EstatesRepository {
  Estate save(Estate person);
}
