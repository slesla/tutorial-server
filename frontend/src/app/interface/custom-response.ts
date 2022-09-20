import { Server } from "./server";

export interface CustomResponse{
  timeStamp: Date;
  statusCode: number;
  status: string;
  reason: string;
  message: string;
  developerMessage: string;
  // the ? signs an optional parameter
  data: { servers?: Server[], server?: Server };
}
