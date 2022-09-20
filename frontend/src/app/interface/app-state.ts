import { DataState } from './../enum/data-state.enum';

// <T> generic type
export interface AppState<T>{
  dataState: DataState;
  appData?: T;
  error?: string;
}
