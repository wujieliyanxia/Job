package com.buer.job.response;


import exception.JobException;

public interface Result<T> {
  Result originOk();

  Result originOk(T body);

  Result originBadRequest(String message);

  Result originUnauthorized();

  Result originNotFound();

  Result serverException(JobException exception);
}
